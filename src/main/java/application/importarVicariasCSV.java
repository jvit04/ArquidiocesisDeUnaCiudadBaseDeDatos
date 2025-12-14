package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

public interface importarVicariasCSV {

    static void importarVicarias(File archivo) throws Exception {
        String sql = "SELECT insert_vicaria(?, ?, ?, ?)";

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

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: NOMBRE_VICARIA (Índice 0) ---
                    String nombreVicaria = datos[0].trim();
                    if (nombreVicaria.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El nombre está vacío y es obligatorio. Se omite el registro.");
                        continue; // Saltamos al siguiente ciclo while (siguiente línea del CSV)
                    }

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: Descripcion (Índice 1) ---
                    String DescVicaria = datos[1].trim();
                    if (DescVicaria.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": La descripcion está vacía. Se omite el registro.");
                        continue;
                    }

                    try {
                        // 1. Nombre Vicaria
                        pstmt.setString(1, datos[0].trim().replace("\uFEFF", ""));

                        // 2. Descripcion Vicaria
                        pstmt.setString(2, datos[1].trim().replace("\uFEFF", ""));

                        // --- OPCIONALES (Manejo de NULL) ---

                        // 3. Email
                        String email = datos[2].trim();
                        if (email.isEmpty() || email.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(3, Types.VARCHAR);
                        } else {
                            pstmt.setString(3, email);
                        }

                        // 4. Telefono
                        String telefono = datos[3].trim();
                        if (telefono.isEmpty() || telefono.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(4, Types.VARCHAR);
                        } else {
                            pstmt.setString(4, telefono);
                        }

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
