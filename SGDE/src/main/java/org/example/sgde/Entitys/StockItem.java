package org.example.sgde.Entitys;

import javafx.beans.property.SimpleStringProperty;

public class StockItem {
    private SimpleStringProperty stock;

    public StockItem(String stock) {
        this.stock = new SimpleStringProperty(stock);
    }

    public String getStock() {
        return this.stock.get();
    }

    public void setStock(String stock) {
        this.stock = new SimpleStringProperty(stock);
    }
}