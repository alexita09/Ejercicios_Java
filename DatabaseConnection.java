/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginescolar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DEVELOPMENT
 */
public class DatabaseConnection {
    private static final String URL      = "jdbc:mysql://localhost:3306/login_escolar";
    private static final String USER     = "root";   // <- ajusta
    private static final String PASSWORD = "admin";   // <- ajusta

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "Driver MySQL no encontrado. Agrega mysql-connector-j al classpath.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


