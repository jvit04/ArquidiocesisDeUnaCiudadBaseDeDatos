package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface importarActividadesCSV {

    static void importarParroquias(File archivo) throws Exception {
        String sql = "SELECT insert_parroquia(?, ?, ?::DATE, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0; // Para saber en qué línea falló

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                // Verificamos que tenga las 5 columnas
                if (datos.length >= 5) {

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: ID_VICARIA (Índice 1) ---
                    String idCultoStr = datos[1].trim();
                    if (idCultoStr.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El ID_VICARIA está vacío y es obligatorio. Se omite el registro.");
                        continue; // Saltamos al siguiente ciclo while (siguiente línea del CSV)
                    }

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: ID FINAL (Índice 8) ---
                    String idFinalStr = datos[8].trim();
                    if (idFinalStr.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El ID FINAL (Clérigo) está vacío. Se omite el registro.");
                        continue;
                    }

                    try {
                        // 1. Nombre Actividad
                        pstmt.setString(1, datos[0].trim().replace("\uFEFF", ""));

                        // 2. ID Lugar Culto
                        pstmt.setInt(2, Integer.parseInt(idCultoStr));

                        // 3. Fecha de la Actividad
                        try {
                            String fechaStr = datos[2].trim();
                            if (fechaStr.isEmpty()) {
                                pstmt.setNull(3, Types.DATE); // O lanza error si la fecha es obligatoria
                            } else {
                                pstmt.setDate(3, Date.valueOf(fechaStr));
                            }
                        } catch (IllegalArgumentException e) {
                            System.err.println("Advertencia en línea " + numeroLinea + ": Fecha inválida, se guardará como NULL.");
                            pstmt.setNull(3, Types.DATE);
                        }

                        // 4. Hora
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        pstmt.setObject(4, LocalDateTime.parse(datos[3].trim(), fmt));

                        // 5. Responsable
                        pstmt.setString(5, datos[4].trim());

                        // Añadir al lote
                        pstmt.addBatch();

                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato numérico en línea " + numeroLinea + ": " + e.getMessage());
                    }

                } else {
                    System.err.println("Línea " + numeroLinea + " omitida: Formato incorrecto (columnas insuficientes).");
                }
            }
            // Ejecutar inserción masiva
            pstmt.executeBatch();
            System.out.println("Proceso finalizado.");
        }
    }
}
