package com.bdular.inventorytracker.data.supplier.data;

import com.bdular.inventorytracker.data.product.pgk.Package;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;


public class DeliveryReport {

    @MongoId
    private String ID;
    @PastOrPresent
    private LocalDateTime date;
    @NotNull
    @DBRef
    private Seller seller;
    @NotEmpty
    @DBRef
    private SupplierOrder supplierOrder;
    @NotEmpty
    @Size(min = 1)
    @DBRef
    private ArrayList<Package> packages;
    @NotEmpty
    @Size(min = 1)
    @DBRef
    private Map<Package, Integer> packagesTotal;
    @NotEmpty
    @Size(min = 1)
    @DBRef
    private Map<Package, Integer> productsTotal;

    public DeliveryReport(@NotNull @PastOrPresent LocalDateTime date,
                          @NotNull Seller seller,
                          @NotEmpty SupplierOrder supplierOrder,
                          @NotEmpty @Size(min = 1) ArrayList<Package> packages,
                          @NotEmpty @Size(min = 1) Map<Package, Integer> packagesTotal,
                          @NotEmpty @Size(min = 1) Map<Package, Integer> productsTotal) {
        this.date = date;
        this.seller = seller;
        this.supplierOrder = supplierOrder;
        this.packages = packages;
        this.packagesTotal = packagesTotal;
        this.productsTotal = productsTotal;
    }
    
    public DeliveryReport(@NotNull Seller seller,
                          @NotEmpty SupplierOrder supplierOrder,
                          @NotEmpty @Size(min = 1) ArrayList<Package> packages,
                          @NotEmpty @Size(min = 1) Map<Package, Integer> packagesTotal,
                          @NotEmpty @Size(min = 1) Map<Package, Integer> productsTotal) {
        this.seller = seller;
        this.supplierOrder = supplierOrder;
        this.packages = packages;
        this.packagesTotal = packagesTotal;
        this.productsTotal = productsTotal;
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

    public String getID() {
        return ID;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public SupplierOrder getSupplierOrder() {
        return supplierOrder;
    }

    public void setSupplierOrder(SupplierOrder supplierOrder) {
        this.supplierOrder = supplierOrder;
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }

    public Map<Package, Integer> getPackagesTotal() {
        return packagesTotal;
    }

    public void setPackagesTotal(Map<Package, Integer> packagesTotal) {
        this.packagesTotal = packagesTotal;
    }

    public Map<Package, Integer> getProductsTotal() {
        return productsTotal;
    }

    public void setProductsTotal(Map<Package, Integer> productsTotal) {
        this.productsTotal = productsTotal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ID", ID)
                .append("date", date)
                .append("seller", seller)
                .append("supplierOrder", supplierOrder)
                .append("packages", packages)
                .append("packagesTotal", packagesTotal)
                .append("productsTotal", productsTotal)
                .toString();
    }
}
