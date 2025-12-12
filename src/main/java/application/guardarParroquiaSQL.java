package application;

import utilities.ConexionBD;

import javax.swing.*;
import java.sql.*;


public interface guardarParroquiaSQL {

//interface para guardar en la base la parroquia

    static void guardarEnSQL(Parroquia p) {
        String sql = "select insert_parroquia(?,?,?,?,?,?,?,?::DATE,?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getVicaria());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getCiudad());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getEmail());
            ps.setString(7, p.getSitioWeb());
            ps.setDate(8, Date.valueOf(p.getFechaFundacion()));
            ps.setInt(9, p.getParroco());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String mensajeDeLaBase = rs.getString(1);
               JOptionPane.showMessageDialog(null,mensajeDeLaBase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al guardar en la Base de Datos: " + e.getMessage());
        }
    }
}
