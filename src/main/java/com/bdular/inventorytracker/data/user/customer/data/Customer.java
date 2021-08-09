package com.bdular.inventorytracker.data.user.customer.data;

import com.bdular.inventorytracker.data.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.math.BigDecimal;


@Document(collection = "customers")
public class Customer extends User {

    @NotEmpty
    private String companyName;
    @NotNull
    @PositiveOrZero
    private Integer reference;
    @PositiveOrZero
    private Integer numberOfOrders;
    @PositiveOrZero
    private BigDecimal orderedTotal;
    @PositiveOrZero
    private BigDecimal paidTotal;

    public Customer(@NotEmpty String name, @NotEmpty String surname, @NotEmpty String address, @NotEmpty String post, @Email String email, @Size(min = 8, max = 10)
    @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
            "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
            "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
            "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
            "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
            "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
            "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
            "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$", message = "VAT ID is not a valid.") String VAT_ID,
                    @NotEmpty String companyName, @NotNull @PositiveOrZero Integer reference) {
        super(name, surname, address, post, email, VAT_ID, false);
        this.companyName = companyName;
        this.reference = reference;
        this.numberOfOrders = 0;
        this.orderedTotal = BigDecimal.ZERO;
        this.paidTotal = BigDecimal.ZERO;
    }

    public Customer(@NotEmpty String name, @NotEmpty String surname, @NotEmpty String address, @NotEmpty String post, @Size(min = 8, max = 10)
    @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
            "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
            "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
            "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
            "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
            "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
            "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
            "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$", message = "VAT ID is not a valid.") String VAT_ID,
                    @NotEmpty String companyName, @NotNull @PositiveOrZero Integer reference) {
        super(name, surname, address, post, VAT_ID, false);
        this.companyName = companyName;
        this.reference = reference;
        this.numberOfOrders = 0;
        this.orderedTotal = BigDecimal.ZERO;
        this.paidTotal = BigDecimal.ZERO;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public BigDecimal getOrderedTotal() {
        return orderedTotal;
    }

    public void setOrderedTotal(BigDecimal orderedTotal) {
        this.orderedTotal = orderedTotal;
    }

    public BigDecimal getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(BigDecimal paidTotal) {
        this.paidTotal = paidTotal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("companyName", companyName)
                .append("reference", reference)
                .append("numberOfOrders", numberOfOrders)
                .append("orderedTotal", orderedTotal)
                .append("paidTotal", paidTotal)
                .toString();
    }
}
