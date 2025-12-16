package application;

import utilities.ConexionBD;
import utilities.ExcepcionAmigable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

public class importarConvenioCSV implements ExcepcionAmigable {

    public static void importarConvenio(File archivo) throws Exception {
        // La consulta llama a la función insert_convenio con 8 parámetros según tu imagen
        String sql = "SELECT insert_convenio(?, ?, ?, ?::DATE, ?::DATE, ?, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                // Verificamos que tenga las 8 columnas necesarias
                if (datos.length >= 8) {

                    // 1. Nombre (Obligatorio) - Limpiamos BOM por si es la primera línea
                    String nombre = datos[0].trim().replace("\uFEFF", "");
                    if (nombre.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Nombre vacío. Registro omitido.");
                        continue;
                    }

                    // 2. Institución (Obligatorio)
                    String institucion = datos[1].trim();
                    if (institucion.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Institución vacía. Registro omitido.");
                        continue;
                    }

                    // 3. Objetivo (Obligatorio - text)
                    String objetivo = datos[2].trim();
                    if (objetivo.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Objetivo vacío. Registro omitido.");
                        continue;
                    }

                    // 4. Fecha Firmante (Obligatorio)
                    String fechaFirma = datos[3].trim();
                    if (fechaFirma.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Fecha de firma vacía. Registro omitido.");
                        continue;
                    }

                    // 5. Fecha Vencimiento (OPCIONAL/NULLABLE)
                    String fechaVencimiento = datos[4].trim();

                    // 6. ID Vicaria (Obligatorio - int)
                    String idVicariaStr = datos[5].trim();
                    if (idVicariaStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": ID Vicaria vacío. Registro omitido.");
                        continue;
                    }

                    // 7. ID Clerigo (Obligatorio - int)
                    String idClerigoStr = datos[6].trim();
                    if (idClerigoStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": ID Clérigo vacío. Registro omitido.");
                        continue;
                    }

                    // 8. Estado (Obligatorio)
                    String estado = datos[7].trim();
                    if (estado.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Estado vacío. Registro omitido.");
                        continue;
                    }

                    try {
                        // Asignación de valores al PreparedStatement en orden estricto de la función

                        pstmt.setString(1, nombre);       // p_nombre
                        pstmt.setString(2, institucion);  // p_institucion
                        pstmt.setString(3, objetivo);     // p_objetivo
                        pstmt.setDate(4, Date.valueOf(fechaFirma)); // p_fecha_firmante

                        // Lógica para Fecha Vencimiento (Único campo NULLABLE)
                        if (fechaVencimiento.isEmpty() || fechaVencimiento.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(5, Types.DATE);
                        } else {
                            pstmt.setDate(5, Date.valueOf(fechaVencimiento));
                        }

                        // Parseo de enteros
                        pstmt.setInt(6, Integer.parseInt(idVicariaStr)); // p_id_vicaria
                        pstmt.setInt(7, Integer.parseInt(idClerigoStr)); // p_id_clerigo

                        pstmt.setString(8, estado); // p_estado

                        // Añadir al lote
                        pstmt.addBatch();

                    } catch (NumberFormatException e) {
                        System.err.println("Error numérico (ID) en línea " + numeroLinea + ": " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error de formato de fecha en línea " + numeroLinea + ": " + e.getMessage());
                    }

                } else {
                    System.err.println("Línea " + numeroLinea + " omitida: Columnas insuficientes.");
                }
            }

            try{
                pstmt.executeBatch();
            }
            catch (SQLException e) {
                ExcepcionAmigable.verificarErrorAmigable(e);
            }
            System.out.println("Proceso de importación de Convenios finalizado.");
        }
    }
}
