package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface importarEventosCSV {

    static void importarEventos(File archivo) throws Exception {
        String sql = "SELECT insert_evento(?, ?, ?::TIMESTAMP, ?::TIMESTAMP, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0;
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                // Verificamos que tenga las 6 columnas necesarias
                if (datos.length >= 6) {

                    // -- 1. Nombre (Obligatorio) --
                    String nombre = datos[0].trim().replace("\uFEFF", "");
                    if (nombre.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Nombre vacío. Registro omitido.");
                        continue;
                    }

                    // -- 2. Categoría (Obligatorio) --
                    String categoria = datos[1].trim();
                    if (categoria.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Categoría vacía. Registro omitido.");
                        continue;
                    }

                    // -- 3. Fecha Inicio (Obligatorio) --
                    String fechaIniStr = datos[2].trim();
                    if (fechaIniStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Fecha Inicio vacía. Registro omitido.");
                        continue;
                    }

                    // -- 4. Fecha Fin (Obligatorio) --
                    String fechaFinStr = datos[3].trim();
                    if (fechaFinStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Fecha Fin vacía. Registro omitido.");
                        continue;
                    }

                    // -- 5. ID Lugar Culto (Obligatorio - Integer) --
                    String idLugarStr = datos[4].trim();
                    if (idLugarStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": ID Lugar Culto vacío. Registro omitido.");
                        continue;
                    }

                    // -- 6. Presupuesto (Obligatorio - Numeric) --
                    String presupuestoStr = datos[5].trim();
                    if (presupuestoStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Presupuesto vacío. Registro omitido.");
                        continue;
                    }

                    try {
                        // Asignación de parámetros

                        // 1. p_nombre
                        pstmt.setString(1, nombre);

                        // 2. p_categoria
                        pstmt.setString(2, categoria);

                        // 3. p_fecha_hora_inicio
                        pstmt.setObject(3, LocalDateTime.parse(fechaIniStr, fmt));

                        // 4. p_fecha_hora_fin
                        pstmt.setObject(4, LocalDateTime.parse(fechaFinStr, fmt));

                        // 5. p_id_lugar_culto
                        pstmt.setInt(5, Integer.parseInt(idLugarStr));

                        // 6. p_presupuesto
                        // Reemplazamos coma por punto por seguridad (ej: 10,50 -> 10.50)
                        pstmt.setBigDecimal(6, new BigDecimal(presupuestoStr.replace(",", ".")));

                        // Añadir al lote
                        pstmt.addBatch();

                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato numérico (ID o Presupuesto) en línea " + numeroLinea + ": " + e.getMessage());
                    } catch (DateTimeParseException e) {
                        System.err.println("Error de formato de Fecha/Hora en línea " + numeroLinea + ": " + e.getMessage());
                    }

                } else {
                    System.err.println("Línea " + numeroLinea + " omitida: Columnas insuficientes (se esperan 6).");
                }
            }
            // Ejecutar inserción masiva
            pstmt.executeBatch();
            System.out.println("Proceso de importación de Eventos finalizado.");
        }
    }
}
