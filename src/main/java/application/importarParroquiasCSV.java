package application;

import utilities.ConexionBD;
import utilities.ExcepcionAmigable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

public class importarParroquiasCSV implements ExcepcionAmigable {

    public static void importarParroquias(File archivo) throws Exception {
        String sql = "SELECT insert_parroquia(?, ?, ?, ?, ?, ?, ?, ?::DATE, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                if (datos.length >= 9) {

                    // -- 1. Nombre (Obligatorio) --
                    String nombre = datos[0].trim().replace("\uFEFF", "");
                    if (nombre.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Nombre vacío. Registro omitido.");
                        continue;
                    }

                    // -- 2. ID Vicaria (Obligatorio) --
                    String idVicariaStr = datos[1].trim();
                    if (idVicariaStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": ID Vicaria vacío. Registro omitido.");
                        continue;
                    }

                    // -- 3. Dirección (Obligatorio) --
                    String direccion = datos[2].trim();
                    if (direccion.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Dirección vacía. Registro omitido.");
                        continue;
                    }

                    // -- 4. Ciudad (Obligatorio) --
                    String ciudad = datos[3].trim();
                    if (ciudad.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Ciudad vacía. Registro omitido.");
                        continue;
                    }

                    // -- 5. Teléfono (OPCIONAL/NULLABLE) --
                    String telefono = datos[4].trim();

                    // -- 6. Email (OPCIONAL/NULLABLE) --
                    String email = datos[5].trim();

                    // -- 7. Sitio Web (OPCIONAL/NULLABLE) --
                    String sitioWeb = datos[6].trim();

                    // -- 8. Fecha Erección (Obligatorio) --
                    String fechaStr = datos[7].trim();
                    if (fechaStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Fecha de Erección vacía. Registro omitido.");
                        continue;
                    }

                    // -- 9. ID Párroco (Obligatorio) --
                    String idParrocoStr = datos[8].trim();
                    if (idParrocoStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": ID Párroco vacío. Registro omitido.");
                        continue;
                    }

                    try {
                        // Asignación de parámetros

                        pstmt.setString(1, nombre);
                        pstmt.setInt(2, Integer.parseInt(idVicariaStr));
                        pstmt.setString(3, direccion);
                        pstmt.setString(4, ciudad);

                        // Teléfono
                        if (telefono.isEmpty() || telefono.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(5, Types.VARCHAR);
                        } else {
                            pstmt.setString(5, telefono);
                        }

                        // Email
                        if (email.isEmpty() || email.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(6, Types.VARCHAR);
                        } else {
                            pstmt.setString(6, email);
                        }

                        // Sitio Web
                        if (sitioWeb.isEmpty() || sitioWeb.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(7, Types.VARCHAR);
                        } else {
                            pstmt.setString(7, sitioWeb);
                        }

                        // Fecha (Validación estricta)
                        pstmt.setDate(8, Date.valueOf(fechaStr));

                        // ID Párroco
                        pstmt.setInt(9, Integer.parseInt(idParrocoStr));

                        // Añadir al lote
                        pstmt.addBatch();

                    } catch (NumberFormatException e) {
                        System.err.println("Error numérico (ID) en línea " + numeroLinea + ": " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error de formato de fecha en línea " + numeroLinea + ": " + e.getMessage());
                    }


                } else {
                    System.err.println("Línea " + numeroLinea + " omitida: Columnas insuficientes (se esperan 9).");
                }
            }
            try{
                pstmt.executeBatch();
            }
            catch (SQLException e) {
                ExcepcionAmigable.verificarErrorAmigable(e);
            }
            System.out.println("Proceso de importación de Parroquias finalizado.");
        }
    }
}