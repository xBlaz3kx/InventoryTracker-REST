package com.bdular.inventorytracker.data.user.seller.data;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Document(collection = "sellers/expenses")
public class Expenses {

    @MongoId
    private String id;
    @DBRef
    private Seller seller;
    @PastOrPresent
    private LocalDateTime date;
    @NotEmpty
    private HashMap<String, BigDecimal> cost = new HashMap<>();
    @NotNull
    private Expense type;

    public Expenses(@PastOrPresent LocalDateTime date,
                    @NotNull HashMap<String, BigDecimal> cost,
                    @NotNull Expense type,
                    Seller seller) {
        this.date = date;
        this.cost = cost;
        this.type = type;
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public HashMap<String, BigDecimal> getCost() {
        return cost;
    }

    public void setCost(HashMap<String, BigDecimal> cost) {
        this.cost = cost;
    }

    public Expense getType() {
        return type;
    }

    public void setType(Expense type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("seller", seller)
                .append("date", date)
                .append("cost", cost)
                .append("type", type)
                .toString();
    }
}
