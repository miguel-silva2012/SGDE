module org.example.sgde {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.sgde to javafx.fxml;
    exports org.example.sgde;
    exports org.example.sgde.Controllers;
    opens org.example.sgde.Controllers to javafx.fxml;
    exports org.example.sgde.Entitys;
    opens org.example.sgde.Entitys to javafx.fxml;
    exports org.example.sgde.DAO;
    opens org.example.sgde.DAO to javafx.fxml;
}