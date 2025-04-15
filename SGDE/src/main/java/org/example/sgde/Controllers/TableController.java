package org.example.sgde.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.example.sgde.Connector;
import org.example.sgde.DAO.ClotheDAO;
import org.example.sgde.Entitys.Clothe;
import org.example.sgde.Entitys.StockItem;

public class TableController {
    @FXML private TableView<StockItem> tableStock;

    @FXML private TableColumn<StockItem, String> InStock;

    @FXML private TableView<Clothe> tableView;

    @FXML public TableColumn<Clothe, String> Name;

    @FXML public TableColumn<Clothe, Double> Price;

    @FXML public TableColumn<Clothe, Integer> Quantity;

    @FXML private HBox confirmBox;

    private final ObservableList<Clothe> clothes = FXCollections.observableArrayList();

    private final ObservableList<StockItem> stock = FXCollections.observableArrayList();

    private int oldSizeClothes;

    private boolean saved;

    @FXML
    public void initialize() {
        saved = false;

        Connector.getColumns(clothes);

        oldSizeClothes = clothes.size();

        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        InStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        editInitializer();

        tableView.setItems(clothes);
        tableStock.setItems(stock);
    }

    @FXML
    public void add() {
        tableView.getItems().add(new Clothe(" ", .0, 0));
        tableStock.getItems().add(new StockItem("Fora de estoque"));
    }

    @FXML
    public void del() {
        if (!clothes.isEmpty()) {
            Clothe selectedClothe = tableView.getSelectionModel().getSelectedItem();
            int selectedClotheIndex = tableView.getSelectionModel().getSelectedIndex();

            int lastIndex = clothes.size() - 1;

            if (selectedClothe != null) {
                tableView.getItems().remove(selectedClothe);
                tableStock.getItems().remove(selectedClotheIndex);
            } else {
                tableView.getItems().remove(lastIndex);
                tableStock.getItems().remove(lastIndex);
            }
        }
    }

    private void setEditableName() {
        Name.setCellFactory(TextFieldTableCell.<Clothe>forTableColumn());

        Name.setOnEditCommit(event -> {
            Clothe clothe = event.getTableView().getItems().get(event.getTablePosition().getRow());
            clothe.setName(event.getNewValue());
        });
    }

    private void setEditablePrice() {
        Price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        Price.setOnEditCommit(event -> {
            Clothe clothe = event.getTableView().getItems().get(event.getTablePosition().getRow());
            clothe.setPrice(event.getNewValue());
        });
    }

    private void setEditableQuantity() {
        if (!clothes.isEmpty()) {
            for (Clothe c : clothes) {
                if (c.isInStock()) {
                    stock.add(new StockItem("Tem no estoque"));
                } else {
                    stock.add(new StockItem("Fora de estoque"));
                }
            }
        }

        setInTableIsInStock();

        Quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        Quantity.setOnEditCommit(event -> {
            Clothe clothe = event.getTableView().getItems().get(event.getTablePosition().getRow());
            clothe.setQuantity(event.getNewValue());

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

            if (clothe.isInStock() && !stock.isEmpty()) {
                stock.get(selectedIndex).setStock("Tem no estoque");
            }

            setInTableIsInStock();
        });
    }

    public void setInTableIsInStock() {
        InStock.setCellFactory(_ -> new TableCell<StockItem, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item);
                    setStyle("-fx-background-color: " + (item.equals("Fora de estoque") ? "red" : "green"));
                }
            }
        });
    }

    private void editInitializer() {
        setEditableName();

        setEditablePrice();

        setEditableQuantity();
    }

    @FXML
    public void finish() {
        if (!saved) {
            saved = true;

            System.out.println("Tamanho antigo da lista: " + oldSizeClothes);
            System.out.println("Tamanho novo da lista: " + clothes.size() + '\n');

            if (!clothes.isEmpty()) {
                for (int i = oldSizeClothes; i < clothes.size(); i++) {
                    new ClotheDAO().addClothe(clothes.get(i));
                }
            }
        }

        confirmBox.setVisible(false);
    }

    @FXML
    public void confirm() {
        confirmBox.setVisible(true);
    }

    @FXML
    public void search() {

    }
}
