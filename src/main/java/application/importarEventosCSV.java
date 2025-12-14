package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface importarEventosCSV {

    static void importarEventos(File archivo) throws Exception {
        String sql = "SELECT insert_evento(?, ?, ?, ?, ?, ?)";

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
                    String idCultoStr = datos[4].trim();
                    if (idCultoStr.isEmpty()) {
                        System.err.println("Error en línea " + numeroLinea + ": El ID_VICARIA está vacío y es obligatorio. Se omite el registro.");
                        continue; // Saltamos al siguiente ciclo while (siguiente línea del CSV)
                    }

                    try {
                        // 1. Nombre Evento
                        pstmt.setString(1, datos[0].trim().replace("\uFEFF", ""));

                        // 2. Categoria
                        pstmt.setString(2, datos[1].trim().replace("\uFEFF", ""));

                        // 3. Fecha hora de inicio
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        pstmt.setObject(3, LocalDateTime.parse(datos[2].trim(), fmt));

                        // 4. Fecha y hora de fin
                        pstmt.setObject(4, LocalDateTime.parse(datos[3].trim(), fmt));

                        // 5. Lugar Culto
                        pstmt.setInt(5, Integer.parseInt(idCultoStr));

                        // 6. Presupuesto
                        BigDecimal presupuesto;
                        try {
                            presupuesto = new BigDecimal(datos[5].trim());
                        } catch (NumberFormatException e) {
                            presupuesto = BigDecimal.ZERO; // O lanza error
                        }
                        pstmt.setBigDecimal(6, presupuesto);

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
