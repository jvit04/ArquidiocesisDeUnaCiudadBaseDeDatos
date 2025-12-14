package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

public interface importarReceptorSacramentoCSV {

    static void importarReceptorSacramento(File archivo) throws Exception {
        String sql = "SELECT insert_receptor_sacramento(?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0; // Para saber en qué línea falló

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                // Verificamos que tenga las 2 columnas
                if (datos.length >= 2) {

                    // --- VALIDACIÓN DE CAMPO OBLIGATORIO: CEDULA_REGISTRO_SACRAMENTO (Índice 1) ---
                    String idRegisSacra = datos[1].trim();
                    if (idRegisSacra.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El ID_REGISTRO_SACRAMENTO está vacío y es obligatorio. Se omite el registro.");
                        continue; // Saltamos al siguiente ciclo while (siguiente línea del CSV)
                    }

                    // 1. Cedula Receptor Sacramento
                    pstmt.setString(1, datos[0].trim().replace("\uFEFF", ""));

                    // 2. ID Registro Sacramento
                    pstmt.setInt(2, Integer.parseInt(idRegisSacra));

                    // Añadir al lote
                    pstmt.addBatch();

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
