package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;

public interface importarRegistroSacramentoCSV {

    static void importarParroquias(File archivo) throws Exception {
        String sql = "SELECT insert_registro_sacramento(?, ?::DATE, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0; // Para saber en qué línea falló

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                // Verificamos que tenga las 4 columnas
                if (datos.length >= 4) {

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: ID_CLERIGO (Índice 2) ---
                    String idClerigoStr = datos[1].trim();
                    if (idClerigoStr.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El ID_CLERIGO está vacío y es obligatorio. Se omite el registro.");
                        continue; // Saltamos al siguiente ciclo while (siguiente línea del CSV)
                    }

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: ID_LUGARES_CULTO (Índice 3) ---
                    String idCultoStr = datos[8].trim();
                    if (idCultoStr.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El ID_LUGARES_CULTO está vacío. Se omite el registro.");
                        continue;
                    }

                    try {
                        // 1. Tipo Sacramento
                        pstmt.setString(1, datos[0].trim().replace("\uFEFF", ""));

                        // 2. Fecha Sacramento
                        try {
                            String fechaStr = datos[1].trim();
                            if (fechaStr.isEmpty()) {
                                pstmt.setNull(2, Types.DATE); // O lanza error si la fecha es obligatoria
                            } else {
                                pstmt.setDate(2, Date.valueOf(fechaStr));
                            }
                        } catch (IllegalArgumentException e) {
                            System.err.println("Advertencia en línea " + numeroLinea + ": Fecha inválida, se guardará como NULL.");
                            pstmt.setNull(2, Types.DATE);
                        }


                        // 3. ID Clerigo (Ya validamos que no está vacío arriba)
                        pstmt.setInt(3, Integer.parseInt(idClerigoStr));

                        // 4. ID Lugares Culto (Ya validamos que no está vacío arriba)
                        pstmt.setInt(4, Integer.parseInt(idCultoStr));

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
