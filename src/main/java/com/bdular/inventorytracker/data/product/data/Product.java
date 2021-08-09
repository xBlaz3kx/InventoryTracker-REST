package com.bdular.inventorytracker.data.product.data;


import com.bdular.inventorytracker.data.supplier.data.Supplier;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING;

@Document(collection = "products")
public class Product {

    @MongoId
    private String ID;
    @NotEmpty
    @Pattern(regexp = "^123456\\d{8}$")
    @Indexed(unique = true, direction = DESCENDING)
    private String barcode;
    @NotEmpty
    private String name;
    @NotEmpty
    private String reference;
    @NotNull
    @PositiveOrZero
    private Integer stock = 0;
    @NotNull
    @Positive
    private Integer SKU = 1;
    @NotNull
    @PositiveOrZero
    private Integer sold = 0;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @Positive
    private BigDecimal purchasePrice;
    private BigDecimal weight;
    @NotNull
    @Positive
    private BigDecimal VAT;
    @PastOrPresent
    private LocalDateTime lastUpdated;
    @NotNull
    @DBRef
    private Supplier supplier;

    public Product(@NotEmpty @Pattern(regexp = "^123456\\d{8}$") String barcode,
                   @NotEmpty String name,
                   @NotEmpty String reference,
                   @NotNull @Positive Integer SKU,
                   @NotNull @Positive BigDecimal price,
                   @NotNull @Positive BigDecimal VAT,
                   @NotNull Supplier supplier) {
        this.barcode = barcode;
        this.name = name;
        this.reference = reference;
        this.SKU = SKU;
        this.price = price;
        this.VAT = VAT;
        this.supplier = supplier;
    }

    public Product(@NotEmpty @Pattern(regexp = "^123456\\d{8}$") String barcode,
                   @NotEmpty String name,
                   @NotEmpty String reference,
                   @NotNull @Positive BigDecimal price,
                   @NotNull @Positive Integer SKU,
                   @NotNull Supplier supplier) {
        this.barcode = barcode;
        this.name = name;
        this.reference = reference;
        this.price = price;
        this.supplier = supplier;
        this.SKU = SKU;
        this.VAT = BigDecimal.valueOf(1.22);
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getSKU() {
        return SKU;
    }

    public Integer getSold() {
        return sold;
    }

    public String getReference() {
        return reference;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setSKU(Integer SKU) {
        this.SKU = SKU;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVAT() {
        return VAT;
    }

    public void setVAT(BigDecimal VAT) {
        this.VAT = VAT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ID", ID)
                .append("barcode", barcode)
                .append("name", name)
                .append("reference", reference)
                .append("stock", stock)
                .append("SKU", SKU)
                .append("sold", sold)
                .append("price", price)
                .append("purchasePrice", purchasePrice)
                .append("weight", weight)
                .append("VAT", VAT)
                .append("lastUpdated", lastUpdated)
                .append("supplier", supplier)
                .toString();
    }
}
