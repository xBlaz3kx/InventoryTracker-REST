package com.bdular.inventorytracker.data.order.data;

import com.bdular.inventorytracker.data.payment.Payment;
import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.user.customer.data.Customer;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "consignments")
public class ConsignmentOrder extends Order {

    @NotEmpty
    @Size(min = 1)
    private Map<Product, Integer> itemsConsigned;

    public ConsignmentOrder(@NotEmpty @Pattern(regexp = "(?:Personal|Post)") String pickupMethod,
                            @NotEmpty String signatureURL, String additionalInfo,
                            @NotNull Payment payment,
                            @Positive BigDecimal total,
                            @Min(0) @Max(1) BigDecimal orderDiscount,
                            @NotEmpty @Size(min = 1) List<Product> productList,
                            @NotEmpty @Size(min = 1) Map<Product, Double> productDiscount,
                            @NotEmpty @Size(min = 1) Map<Product, Boolean> hasOrderDiscount,
                            @NotNull Customer customerReference,
                            @NotNull Seller sellerReference,
                            @NotEmpty @Size(min = 1) Map<Product, Integer> itemsConsigned) {
        super(pickupMethod,
                signatureURL,
                additionalInfo,
                total,
                orderDiscount,
                payment,
                productList,
                new HashMap<>(),
                productDiscount,
                hasOrderDiscount,
                customerReference,
                sellerReference);
        this.itemsConsigned = itemsConsigned;
    }

    public Map<Product, Integer> getItemsConsigned() {
        return itemsConsigned;
    }

    public void setItemsConsigned(Map<Product, Integer> itemsConsigned) {
        this.itemsConsigned = itemsConsigned;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("itemsConsigned", itemsConsigned)
                .toString();
    }
}
