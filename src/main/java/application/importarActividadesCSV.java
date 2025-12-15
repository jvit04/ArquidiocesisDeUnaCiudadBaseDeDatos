package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time; // Importante para el campo 'hora'

public interface importarActividadesCSV {

    static void importarActividades(File archivo) throws Exception {
        String sql = "SELECT insert_actividades(?, ?, ?::DATE, ?::TIME, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                // Verificamos que tenga las 5 columnas necesarias
                if (datos.length >= 5) {

                    // -- 1. Nombre (Obligatorio) --
                    String nombre = datos[0].trim().replace("\uFEFF", "");
                    if (nombre.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Nombre vacío. Registro omitido.");
                        continue;
                    }

                    // -- 2. ID Lugar Culto (Obligatorio - Integer) --
                    String idLugarStr = datos[1].trim();
                    if (idLugarStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": ID Lugar de Culto vacío. Registro omitido.");
                        continue;
                    }

                    // -- 3. Fecha (Obligatorio - Date) --
                    String fechaStr = datos[2].trim();
                    if (fechaStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Fecha vacía. Registro omitido.");
                        continue;
                    }

                    // -- 4. Hora (Obligatorio - Time) --
                    String horaStr = datos[3].trim();
                    if (horaStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Hora vacía. Registro omitido.");
                        continue;
                    }

                    // -- 5. Responsable (Obligatorio - Varchar) --
                    String responsable = datos[4].trim();
                    if (responsable.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Responsable vacío. Registro omitido.");
                        continue;
                    }

                    try {
                        // Asignación de parámetros (Orden estricto de la función)

                        // 1. p_nombre
                        pstmt.setString(1, nombre);

                        // 2. p_id_lugares_culto
                        pstmt.setInt(2, Integer.parseInt(idLugarStr));

                        // 3. p_fecha (Conversión estricta, si falla salta al catch)
                        pstmt.setDate(3, Date.valueOf(fechaStr));

                        // 4. p_hora
                        if (horaStr.length() == 5) { horaStr += ":00"; }
                        pstmt.setTime(4, Time.valueOf(horaStr));

                        // 5. p_responsable
                        pstmt.setString(5, responsable);

                        // Añadir al lote
                        pstmt.addBatch();

                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato numérico en línea " + numeroLinea + ": " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error de formato de Fecha/Hora en línea " + numeroLinea + ": " + e.getMessage());
                    }

                } else {
                    System.err.println("Línea " + numeroLinea + " omitida: Columnas insuficientes (se esperan 5).");
                }
            }
            // Ejecutar inserción masiva
            pstmt.executeBatch();
            System.out.println("Proceso de importación de Actividades finalizado.");
        }
    }
}
