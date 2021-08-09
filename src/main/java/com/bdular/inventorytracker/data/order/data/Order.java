package com.bdular.inventorytracker.data.order.data;


import com.bdular.inventorytracker.data.payment.Payment;
import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.user.customer.data.Customer;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import com.mongodb.lang.Nullable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.bdular.inventorytracker.data.order.data.OrderStatus.SUBMITTED;

@Document(collection = "orders")
public class Order {

    @MongoId
    private String ID;
    @NotEmpty
    @Pattern(regexp = "(?:Personal|Post)")
    private String pickupMethod;
    @NotEmpty
    private String signatureURL;
    private String additionalInfo;
    @Positive
    private BigDecimal total;
    @Min(0)
    @Max(1)
    private BigDecimal orderDiscount;
    @NotNull
    private OrderStatus status;
    @NotNull
    private Payment paymentInfo;
    @NotEmpty
    @Size(min = 1)
    @DBRef
    private List<Product> productList;
    @NotEmpty
    @Size(min = 1)
    @DBRef
    private Map<Product, Integer> productNumbers;
    @NotEmpty
    @Size(min = 1)
    @DBRef
    private Map<Product, Double> productDiscount;
    @NotEmpty
    @Size(min = 1)
    @DBRef
    private Map<Product, Boolean> hasOrderDiscount;
    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;
    @PositiveOrZero
    private Integer reference;
    @NotNull
    @DBRef
    private Customer customerReference;
    @NotNull
    @DBRef
    private Seller sellerReference;

    public Order(@NotEmpty @Pattern(regexp = "(?:Personal|Post)") String pickupMethod,
                 @NotEmpty String signatureURL,
                 @Nullable String additionalInfo,
                 @Positive BigDecimal total,
                 @Min(0) @Max(1) BigDecimal orderDiscount,
                 @NotNull Payment payment,
                 @NotEmpty @Size(min = 1) List<Product> productList,
                 @NotEmpty @Size(min = 1) Map<Product, Integer> productNumbers,
                 @NotEmpty @Size(min = 1) Map<Product, Double> productDiscount,
                 @NotEmpty @Size(min = 1) Map<Product, Boolean> hasOrderDiscount,
                 @NotNull Customer customerReference,
                 @NotNull Seller sellerReference) {
        this.pickupMethod = pickupMethod;
        this.signatureURL = signatureURL;
        this.additionalInfo = additionalInfo;
        this.total = total;
        this.orderDiscount = orderDiscount;
        this.paymentInfo = payment;
        this.status = SUBMITTED;
        this.productList = productList;
        this.productNumbers = productNumbers;
        this.productDiscount = productDiscount;
        this.hasOrderDiscount = hasOrderDiscount;
        this.customerReference = customerReference;
        this.sellerReference = sellerReference;
    }

    public String getPickupMethod() {
        return pickupMethod;
    }

    public void setPickupMethod(String pickupMethod) {
        this.pickupMethod = pickupMethod;
    }

    public String getSignatureURL() {
        return signatureURL;
    }

    public void setSignatureURL(String signatureURL) {
        this.signatureURL = signatureURL;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(BigDecimal orderDiscount) {
        this.orderDiscount = orderDiscount;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Map<Product, Integer> getProductNumbers() {
        return productNumbers;
    }

    public void setProductNumbers(Map<Product, Integer> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public Map<Product, Double> getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(Map<Product, Double> productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Map<Product, Boolean> getHasOrderDiscount() {
        return hasOrderDiscount;
    }

    public void setHasOrderDiscount(Map<Product, Boolean> hasOrderDiscount) {
        this.hasOrderDiscount = hasOrderDiscount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public Customer getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(Customer customerReference) {
        this.customerReference = customerReference;
    }

    public Seller getSellerReference() {
        return sellerReference;
    }

    public void setSellerReference(Seller sellerReference) {
        this.sellerReference = sellerReference;
    }

    public Payment getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(Payment paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ID", ID)
                .append("pickupMethod", pickupMethod)
                .append("signatureURL", signatureURL)
                .append("additionalInfo", additionalInfo)
                .append("total", total)
                .append("orderDiscount", orderDiscount)
                .append("status", status)
                .append("paymentInfo", paymentInfo)
                .append("productList", productList)
                .append("productNumbers", productNumbers)
                .append("productDiscount", productDiscount)
                .append("hasOrderDiscount", hasOrderDiscount)
                .append("timestamp", timestamp)
                .append("reference", reference)
                .append("customerReference", customerReference)
                .append("sellerReference", sellerReference)
                .toString();
    }
}

