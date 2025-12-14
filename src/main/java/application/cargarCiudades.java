package application;

import utilities.Paths;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface cargarCiudades {
    static List<String> cargarCiudades() {
        List<String> ciudades = new ArrayList<>();


        String sql = "SELECT * FROM obtener_ciudades()";
        try (Connection conn = DriverManager.getConnection(Paths.UrlBaseDatos, Paths.USER, Paths.PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String nombresDesdeBD = rs.getString("ciudades");

                ciudades.add(nombresDesdeBD);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ciudades;
    }
}

