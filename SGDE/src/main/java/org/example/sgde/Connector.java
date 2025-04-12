package org.example.sgde;

import javafx.collections.ObservableList;
import org.example.sgde.Entitys.Clothe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector {
    private static final String user = "root",
                password = "root",
                URL = "jdbc:mysql://localhost:3306/clothes";

    private static Connection conn;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (conn == null) {
                conn = DriverManager.getConnection(URL, user, password);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    public static void getColumns(ObservableList<Clothe> list) {
        try {
            ResultSet rsClothe = conn.createStatement().executeQuery("SELECT * FROM clothes");

            while (rsClothe.next()) {
                list.add(new Clothe(rsClothe.getString("name"),
                        Double.parseDouble(rsClothe.getString("price")), Integer.parseInt(rsClothe.getString("quantity"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
