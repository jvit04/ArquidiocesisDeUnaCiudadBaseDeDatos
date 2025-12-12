package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;

public interface importarParroquiasCSV {

    static void importarParroquias(File archivo) throws Exception {
        String sql = "SELECT registro_parroquias(?, ?, ?, ?, ?, ?, ?, ?::DATE, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0; // Para saber en qué línea falló

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                // Verificamos que tenga las 9 columnas
                if (datos.length >= 9) {

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: ID_VICARIA (Índice 1) ---
                    String idVicariaStr = datos[1].trim();
                    if (idVicariaStr.isEmpty()) {
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
                        // 1. Nombre Parroquia
                        pstmt.setString(1, datos[0].trim().replace("\uFEFF", ""));

                        // 2. ID Vicaria (Ya validamos que no está vacío arriba)
                        pstmt.setInt(2, Integer.parseInt(idVicariaStr));

                        // 3. Dirección
                        pstmt.setString(3, datos[2].trim());

                        // 4. Ciudad
                        pstmt.setString(4, datos[3].trim());

                        // --- OPCIONALES (Manejo de NULL) ---

                        // 5. Teléfono
                        String telefono = datos[4].trim();
                        if (telefono.isEmpty() || telefono.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(5, Types.VARCHAR);
                        } else {
                            pstmt.setString(5, telefono);
                        }

                        // 6. Email
                        String email = datos[5].trim();
                        if (email.isEmpty() || email.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(6, Types.VARCHAR);
                        } else {
                            pstmt.setString(6, email);
                        }

                        // 7. Sitio Web
                        String sitioWeb = datos[6].trim();
                        if (sitioWeb.isEmpty() || sitioWeb.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(7, Types.VARCHAR);
                        } else {
                            pstmt.setString(7, sitioWeb);
                        }

                        // 8. Fecha Creación
                        try {
                            String fechaStr = datos[7].trim();
                            if (fechaStr.isEmpty()) {
                                pstmt.setNull(8, Types.DATE); // O lanza error si la fecha es obligatoria
                            } else {
                                pstmt.setDate(8, Date.valueOf(fechaStr));
                            }
                        } catch (IllegalArgumentException e) {
                            System.err.println("Advertencia en línea " + numeroLinea + ": Fecha inválida, se guardará como NULL.");
                            pstmt.setNull(8, Types.DATE);
                        }

                        // 9. ID Final (Ya validamos que no está vacío arriba)
                        pstmt.setInt(9, Integer.parseInt(idFinalStr));

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
