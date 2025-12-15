package application;

import utilities.ConexionBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;

public interface importarClerigoCSV {

        static void importarClerigo(File archivo) throws Exception {
            String sql = "SELECT insert_clerigo(?, ?, ?, ?, ?, ?::DATE, ?::DATE, ?, ?)";

            try (Connection connection = ConexionBD.conectar();
                 PreparedStatement pstmt = connection.prepareStatement(sql);
                 BufferedReader br = new BufferedReader(new FileReader(archivo))) {

                String linea;
                int numeroLinea = 0; // Para saber en qué línea falló

                while ((linea = br.readLine()) != null) {
                    numeroLinea++;
                    String[] datos = linea.split(";", -1);

                    // Verificamos que tenga las 9 columnas
                    if (datos.length >= 8) {
                        String nombres = datos[0].trim().replace("\uFEFF", "");
                        if (nombres.isEmpty()) {
                            System.err.println("Error en línea " + numeroLinea + ": El nombre está vacío y es obligatorio. Se omite el registro.");
                            continue;
                        }

                        String apellidos = datos[1].trim();
                        if (apellidos.isEmpty()) {
                            System.err.println("Error en línea " + numeroLinea + ": El apellido está vacío. Se omite el registro.");
                            continue;
                        }
                        String cedula = datos[2].trim();
                        if (cedula.isEmpty()) {
                            System.err.println("Error en línea " + numeroLinea + ": El campo cedula está vacío y es obligatorio. Se omite el registro.");
                            continue;
                        }
                        String rol = datos[3].trim();
                        if (rol.isEmpty()) {
                            System.err.println("Error en línea " + numeroLinea + ": El rol está vacío y es obligatorio. Se omite el registro.");
                            continue;
                        }
                        String fecha_n = datos[4].trim();
                        if (fecha_n.isEmpty()) {
                            System.err.println("Error en línea " + numeroLinea + ": El campo fecha está vacío y es obligatorio. Se omite el registro.");
                            continue;
                        }
                        String fecha_o = datos[5].trim();
                        if (fecha_o.isEmpty()) {
                            System.err.println("Error en línea " + numeroLinea + ": El campo fecha está vacío y es obligatorio. Se omite el registro.");
                            continue;
                        }

                        try {
                            // 1. Nombre Clero
                            pstmt.setString(1, nombres);

                            // 2. Apellido
                            pstmt.setInt(2, Integer.parseInt(nombres));

                            // 3. Cedula
                            pstmt.setString(3, cedula);

                            // 4. rol
                            pstmt.setString(4, rol);

                            pstmt.setDate(5, Date.valueOf(fecha_n));
                            pstmt.setDate(6, Date.valueOf(fecha_o));


                            // 7. Email
                            String email = datos[7].trim();
                            if (email.isEmpty() || email.equalsIgnoreCase("NULL")) {
                                pstmt.setNull(7, Types.VARCHAR);
                            } else {
                                pstmt.setString(7, email);
                            }
                            // 8. Teléfono
                            String telefono = datos[8].trim();
                            if (telefono.isEmpty() || telefono.equalsIgnoreCase("NULL")) {
                                pstmt.setNull(8, Types.VARCHAR);
                            } else {
                                pstmt.setString(8, telefono);
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


