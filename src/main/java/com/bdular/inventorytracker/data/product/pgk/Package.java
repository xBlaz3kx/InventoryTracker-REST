package com.bdular.inventorytracker.data.product.pgk;

import com.bdular.inventorytracker.data.product.data.Product;
import com.mongodb.lang.Nullable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Document(collection = "packages")
public class Package {

    @MongoId
    private String ID;
    @NotNull
    @Positive
    private Integer numberOfProductsInPackage;
    @NotNull
    @PositiveOrZero
    private Integer received;
    @Nullable
    @PositiveOrZero
    private Double weight;
    @Nullable
    @PositiveOrZero
    private Double height;
    @Nullable
    @PositiveOrZero
    private Double depth;
    @Nullable
    @PositiveOrZero
    private Double width;
    @NotEmpty
    @Pattern(regexp = "^123456\\d{8}$")
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String barcode;
    @NotEmpty
    private String productName;
    @NotNull
    @DBRef
    private Product productReference;
    @Nullable
    private LocalDateTime lastReceived;

    public Package(@NotNull @Positive Integer numberOfProductsInPackage,
                   @Nullable @PositiveOrZero Double weight,
                   @Nullable @PositiveOrZero Double height,
                   @Nullable @PositiveOrZero Double depth,
                   @Nullable @PositiveOrZero Double width,
                   @NotEmpty @Pattern(regexp = "^123456\\d{8}$") String barcode,
                   @NotEmpty String productName,
                   @NotNull Product productReference) {
        this.numberOfProductsInPackage = numberOfProductsInPackage;
        this.weight = weight;
        this.height = height;
        this.depth = depth;
        this.width = width;
        this.barcode = barcode;
        this.productName = productName;
        this.productReference = productReference;
    }

    public Package(@NotNull @Positive Integer numberOfProductsInPackage,
                   @NotEmpty @Pattern(regexp = "^123456\\d{8}$") String barcode,
                   @NotEmpty String productName,
                   @NotNull Product productReference) {
        this.numberOfProductsInPackage = numberOfProductsInPackage;
        this.barcode = barcode;
        this.productName = productName;
        this.productReference = productReference;
        this.received = 0;
        this.depth = 0.0;
        this.height = 0.0;
        this.weight = 0.0;
        this.width = 0.0;
    }

    public Integer getNumberOfProductsInPackage() {
        return numberOfProductsInPackage;
    }

    public void setNumberOfProductsInPackage(Integer numberOfProductsInPackage) {
        this.numberOfProductsInPackage = numberOfProductsInPackage;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDateTime getLastReceived() {
        return lastReceived;
    }

    public void setLastReceived(LocalDateTime lastReceived) {
        this.lastReceived = lastReceived;
    }

    public String getID() {
        return ID;
    }

    public Product getProductReference() {
        return productReference;
    }

    public void setProductReference(Product productReference) {
        this.productReference = productReference;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ID", ID)
                .append("numberOfProductsInPackage", numberOfProductsInPackage)
                .append("received", received)
                .append("weight", weight)
                .append("height", height)
                .append("depth", depth)
                .append("width", width)
                .append("barcode", barcode)
                .append("productName", productName)
                .append("productReference", productReference)
                .append("lastReceived", lastReceived)
                .toString();
    }
}

