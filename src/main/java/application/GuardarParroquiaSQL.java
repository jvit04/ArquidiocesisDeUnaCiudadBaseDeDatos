package application;

import utilities.ConexionBD;
import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class GuardarParroquiaSQL {



    public static void guardarEnSQL(Parroquia p) {
        String sql = "INSERT INTO parroquias (nombre, vicaria, ciudad, direccion, parroco, telefono, sitio_web, email, fecha_fundacion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getVicaria());
            ps.setString(3, p.getCiudad());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getParroco());
            ps.setString(6, p.getTelefono());
            ps.setString(7, p.getSitioWeb());
            ps.setString(8, p.getEmail());

            ps.setDate(9, Date.valueOf(p.getFechaFundacion()));

            //Inserción
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Parroquia guardada exitosamente en la base de datos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al guardar en la Base de Datos: " + e.getMessage());
        }
    }
}
