package com.bdular.inventorytracker.data.supplier.data;


import com.bdular.inventorytracker.data.order.data.OrderStatus;
import com.bdular.inventorytracker.data.product.data.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

import static com.bdular.inventorytracker.data.order.data.OrderStatus.SUBMITTED;

@Document(collection = "/suppliers/orders")
public class SupplierOrder {

    @MongoId
    private String ID;
    @PastOrPresent
    private LocalDateTime orderDate;
    private LocalDateTime expectedDelivery;
    @NotNull
    private OrderStatus status;
    private BigDecimal value;
    @NotEmpty
    @DBRef
    private Supplier supplier;
    @NotEmpty
    @DBRef
    @Size(min = 1)
    private HashMap<Product, Integer> productsOrdered;
    @DBRef
    private SupplierOrderTemplate template;
    @DBRef
    private DeliveryReport report;

    public SupplierOrder(@NotEmpty Supplier supplier, @NotEmpty @Size(min = 1) HashMap<Product, Integer> productsOrdered) {
        this.supplier = supplier;
        this.status = SUBMITTED;
        this.productsOrdered = productsOrdered;
        this.orderDate = LocalDateTime.now();
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getID() {
        return ID;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public HashMap<Product, Integer> getProductsOrdered() {
        return productsOrdered;
    }

    public void setProductsOrdered(HashMap<Product, Integer> productsOrdered) {
        this.productsOrdered = productsOrdered;
    }

    public DeliveryReport getReport() {
        return report;
    }

    public void setReport(DeliveryReport report) {
        this.report = report;
    }

    public SupplierOrderTemplate getTemplate() {
        return template;
    }

    public void setTemplate(SupplierOrderTemplate template) {
        this.template = template;
    }

    public LocalDateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(LocalDateTime expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ID", ID)
                .append("orderURL", template)
                .append("supplier", supplier)
                .append("orderDate", orderDate)
                .append("expectedDelivery", expectedDelivery)
                .append("status", status)
                .append("value", value)
                .append("productsOrdered", productsOrdered)
                .append("report", report)
                .toString();
    }
}
