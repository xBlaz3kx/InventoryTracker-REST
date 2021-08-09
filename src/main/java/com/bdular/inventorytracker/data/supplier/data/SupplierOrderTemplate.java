package com.bdular.inventorytracker.data.supplier.data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;

@Document(collection = "supplier/order/templates")
public class SupplierOrderTemplate {

    @MongoId
    String id;
    @NotNull
    @DBRef
    Supplier supplier;
    Binary file;

    public SupplierOrderTemplate(@NotNull Supplier supplier) {
        this.supplier = supplier;
    }

    public String getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Binary getFile() {
        return file;
    }

    public void setFile(Binary file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("supplier", supplier)
                .append("file", file)
                .toString();
    }
}
