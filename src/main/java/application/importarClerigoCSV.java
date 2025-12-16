package application;

import utilities.ConexionBD;
import utilities.ExcepcionAmigable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
//Permite importar desde un CSV los datos de la tabla correspondiente a la base de datos.
public class importarClerigoCSV implements ExcepcionAmigable {

  public  static void importarClerigo(File archivo) throws Exception {
        String sql = "SELECT insert_clerigo(?, ?, ?, ?, ?::DATE, ?::DATE, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                String[] datos = linea.split(";", -1);

                if (datos.length >= 8) {

                    // -- 1. Nombres (Obligatorio) --
                    String nombres = datos[0].trim().replace("\uFEFF", "");
                    if (nombres.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Nombre vacío. Registro omitido.");
                        continue;
                    }

                    // -- 2. Apellidos (Obligatorio) --
                    String apellidos = datos[1].trim();
                    if (apellidos.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Apellido vacío. Registro omitido.");
                        continue;
                    }

                    // -- 3. Cédula (Obligatorio) --
                    String cedula = datos[2].trim();
                    if (cedula.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Cédula vacía. Registro omitido.");
                        continue;
                    }

                    // -- 4. Rol (Obligatorio) --
                    String rol = datos[3].trim();
                    if (rol.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Rol vacío. Registro omitido.");
                        continue;
                    }

                    // -- 5. Fecha Nacimiento (Obligatorio) --
                    String fechaNacStr = datos[4].trim();
                    if (fechaNacStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Fecha Nacimiento vacía. Registro omitido.");
                        continue;
                    }

                    // -- 6. Fecha Ordenación (Obligatorio) --
                    String fechaOrdStr = datos[5].trim();
                    if (fechaOrdStr.isEmpty()) {
                        System.err.println("Línea " + numeroLinea + ": Fecha Ordenación vacía. Registro omitido.");
                        continue;
                    }

                    try {
                        // Asignación de parámetros

                        // 1. Nombres
                        pstmt.setString(1, nombres);

                        // 2. Apellidos (CORREGIDO: Antes era setInt)
                        pstmt.setString(2, apellidos);

                        // 3. Cédula
                        pstmt.setString(3, cedula);

                        // 4. Rol
                        pstmt.setString(4, rol);

                        // 5. Fecha Nacimiento
                        pstmt.setDate(5, Date.valueOf(fechaNacStr));

                        // 6. Fecha Ordenación
                        pstmt.setDate(6, Date.valueOf(fechaOrdStr));

                        // -- 7. Email (OPCIONAL/NULLABLE) --
                        String email = datos[6].trim();
                        if (email.isEmpty() || email.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(7, Types.VARCHAR); // Usamos VARCHAR o CLOB para 'text'
                        } else {
                            pstmt.setString(7, email);
                        }

                        // -- 8. Teléfono (OPCIONAL/NULLABLE) --
                        String telefono = datos[7].trim(); // CORREGIDO: índice 7 (el octavo elemento)
                        if (telefono.isEmpty() || telefono.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(8, Types.VARCHAR);
                        } else {
                            pstmt.setString(8, telefono);
                        }

                        // Añadir al lote
                        pstmt.addBatch();

                    } catch (NumberFormatException e) {
                        System.err.println("Error numérico (ID) en línea " + numeroLinea + ": " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error de formato de fecha en línea " + numeroLinea + ": " + e.getMessage());
                    }
                    catch (Exception e) {
                        System.err.println("Error inesperado en línea " + numeroLinea + ": " + e.getMessage());
                    }

                } else {
                    System.err.println("Línea " + numeroLinea + " omitida: Columnas insuficientes (se esperan 8).");
                }
            }
            try{
                pstmt.executeBatch();
            }
            catch (SQLException e) {
                ExcepcionAmigable.verificarErrorAmigable(e);
            }
            System.out.println("Proceso de importación de Clérigos finalizado.");
        }
    }
}