package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;

public interface importarProyectosPastoralesCSV {

    static void importarProyectosPastorales(File archivo) throws Exception {
        // La consulta llama a la función insert_proyectos_pastorales con 7 parámetros
        String sql = "SELECT insert_proyectos_pastorales(?, ?, ?, ?::DATE, ?::DATE, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                // Verificamos que tenga las 7 columnas necesarias
                if (datos.length >= 7) {

                    // -- 1. Nombre (Obligatorio) --
                    String nombre = datos[0].trim().replace("\uFEFF", "");
                    if (nombre.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Nombre vacío. Registro omitido.");
                        continue;
                    }

                    // -- 2. Descripción (Obligatorio - text) --
                    String descripcion = datos[1].trim();
                    if (descripcion.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Descripción vacía. Registro omitido.");
                        continue;
                    }

                    // -- 3. ID Pastorales (Obligatorio - int) --
                    String idPastoralesStr = datos[2].trim();
                    if (idPastoralesStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": ID Pastorales vacío. Registro omitido.");
                        continue;
                    }

                    // -- 4. Fecha Inicio (Obligatorio - date) --
                    String fechaInicio = datos[3].trim();
                    if (fechaInicio.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Fecha de inicio vacía. Registro omitido.");
                        continue;
                    }

                    // -- 5. Fecha Fin (OPCIONAL/NULLABLE - date) --
                    String fechaFin = datos[4].trim();

                    // -- 6. Presupuesto (Obligatorio - numeric) --
                    String presupuestoStr = datos[5].trim();
                    if (presupuestoStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Presupuesto vacío. Registro omitido.");
                        continue;
                    }

                    // -- 7. Estado (Obligatorio - varchar) --
                    String estado = datos[6].trim();
                    if (estado.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Estado vacío. Registro omitido.");
                        continue;
                    }

                    try {
                        // Asignación de parámetros

                        // 1. p_nombre
                        pstmt.setString(1, nombre);

                        // 2. p_descripcion
                        pstmt.setString(2, descripcion);

                        // 3. p_id_pastorales
                        pstmt.setInt(3, Integer.parseInt(idPastoralesStr));

                        // 4. p_fecha_inicio
                        pstmt.setDate(4, Date.valueOf(fechaInicio));

                        // 5. p_fecha_fin (Único NULLABLE)
                        if (fechaFin.isEmpty() || fechaFin.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(5, Types.DATE);
                        } else {
                            pstmt.setDate(5, Date.valueOf(fechaFin));
                        }

                        // 6. p_presupuesto (Numeric -> BigDecimal)
                        String presupuestoLimpio = presupuestoStr.replace(",", ".");
                        pstmt.setBigDecimal(6, new BigDecimal(presupuestoLimpio));

                        // 7. p_estado
                        pstmt.setString(7, estado);

                        // Añadir al lote
                        pstmt.addBatch();

                    } catch (NumberFormatException e) {
                        System.err.println("Error numérico (ID o Presupuesto) en línea " + numeroLinea + ": " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error en formato de fecha en línea " + numeroLinea + ": " + e.getMessage());
                    }

                } else {
                    System.err.println("Línea " + numeroLinea + " omitida: Columnas insuficientes (se esperan 7).");
                }
            }

            // Ejecutar inserción masiva
            pstmt.executeBatch();
            System.out.println("Proceso de importación de Proyectos Pastorales finalizado.");
        }
    }
}