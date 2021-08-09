package com.bdular.inventorytracker.data.inventory.data;

import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

@Document(collection = "inventory")
public class InventoryReport {

    @MongoId
    private String id;
    @NotEmpty
    @DBRef
    private ArrayList<Product> products;
    @NotEmpty
    @DBRef
    private Map<Product, Integer> currentStock;
    @NotEmpty
    @DBRef
    private Map<Product, Integer> expectedStock;
    private LocalDateTime date;
    @NotEmpty
    @DBRef
    private Seller seller;
    private BigDecimal expectedInventoryValue;
    private BigDecimal actualInventoryValue;

    public InventoryReport(ArrayList<Product> products, Map<Product, Integer> currentStock, Map<Product, Integer> expectedStock, Seller seller) {
        this.products = products;
        this.currentStock = currentStock;
        this.expectedStock = expectedStock;
        this.seller = seller;
        this.date = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Map<Product, Integer> getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Map<Product, Integer> currentStock) {
        this.currentStock = currentStock;
    }

    public Map<Product, Integer> getExpectedStock() {
        return expectedStock;
    }

    public void setExpectedStock(Map<Product, Integer> expectedStock) {
        this.expectedStock = expectedStock;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public BigDecimal getExpectedInventoryValue() {
        return expectedInventoryValue;
    }

    public void setExpectedInventoryValue(BigDecimal expectedInventoryValue) {
        this.expectedInventoryValue = expectedInventoryValue;
    }

    public BigDecimal getActualInventoryValue() {
        return actualInventoryValue;
    }

    public void setActualInventoryValue(BigDecimal actualInventoryValue) {
        this.actualInventoryValue = actualInventoryValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("products", products)
                .append("currentStock", currentStock)
                .append("expectedStock", expectedStock)
                .append("date", date)
                .append("seller", seller)
                .append("expectedInventoryValue", expectedInventoryValue)
                .append("actualInventoryValue", actualInventoryValue)
                .toString();
    }
}
