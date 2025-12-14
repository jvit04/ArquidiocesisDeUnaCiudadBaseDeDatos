package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;

public interface importarResponsableCSV {

    static void importarResponsable(File archivo) throws Exception {
        String sql = "SELECT insert_responsable(?, ?)";

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
                    String idParroquiaStr = datos[0].trim();
                    if (idParroquiaStr.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El ID_VICARIA está vacío y es obligatorio. Se omite el registro.");
                        continue; // Saltamos al siguiente ciclo while (siguiente línea del CSV)
                    }

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: ID FINAL (Índice 8) ---
                    String idProyPastorStr = datos[1].trim();
                    if (idProyPastorStr.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El ID FINAL (Clérigo) está vacío. Se omite el registro.");
                        continue;
                    }

                    try {
                        // 1. ID Parroquia
                        pstmt.setInt(1, Integer.parseInt(idParroquiaStr));

                        // 2. ID Proyectos Pastorales
                        pstmt.setInt(2, Integer.parseInt(idProyPastorStr));

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
