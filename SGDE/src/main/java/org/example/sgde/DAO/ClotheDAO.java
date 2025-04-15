package org.example.sgde.DAO;

import javafx.collections.ObservableList;
import org.example.sgde.Connector;
import org.example.sgde.Entitys.Clothe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClotheDAO {
    public void addClothe(Clothe clothe) {
        String sql = "INSERT INTO clothes VALUES (DEFAULT, ?, ?, ?)";

        try {
            PreparedStatement ps = Connector.getConnection().prepareStatement(sql);

            ps.setString(1, clothe.getName());
            ps.setString(2, String.valueOf(clothe.getPrice()));
            ps.setString(3, String.valueOf(clothe.getQuantity()));

            System.out.println(ps);

            ps.execute();
            ps.close();
        } catch (SQLException _) {}
    }
}
