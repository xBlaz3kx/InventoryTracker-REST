package com.bdular.inventorytracker.data.user.seller.data;

import com.bdular.inventorytracker.data.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Document(collection = "sellers")
public class Seller extends User {

    @Positive
    private Integer consecutiveOrderNumber;
    @DBRef
    private List<Expenses> yearlyExpenses = new ArrayList<>();
    private BigDecimal productSoldValue = ZERO;

    public Seller(@NotEmpty String name,
                  @NotEmpty String surname,
                  @NotEmpty String address,
                  @NotEmpty String post,
                  @Email String email,
                  @Size(min = 8, max = 10)
                  @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
                          "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
                          "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
                          "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
                          "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
                          "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
                          "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
                          "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$", message = "VAT ID is not a valid.") String VAT_ID,
                  @NotNull boolean isSuperUser,
                  @Positive Integer consecutiveOrderNumber) {
        super(name, surname, address, post, email, VAT_ID, isSuperUser);
        this.consecutiveOrderNumber = consecutiveOrderNumber;
    }

    public Seller(@NotEmpty String name,
                  @NotEmpty String surname,
                  @NotEmpty String address,
                  @NotEmpty String post,
                  @Size(min = 8, max = 10) @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
                          "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
                          "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
                          "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
                          "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
                          "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
                          "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
                          "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$", message = "VAT ID is not a valid.") String VAT_ID,
                  @Positive Integer consecutiveOrderNumber,
                  List<Expenses> yearlyExpenses) {
        super(name, surname, address, post, VAT_ID, false);
        this.consecutiveOrderNumber = consecutiveOrderNumber;
        this.yearlyExpenses = yearlyExpenses;
    }

    public Integer getConsecutiveOrderNumber() {
        return consecutiveOrderNumber;
    }

    public void setConsecutiveOrderNumber(Integer consecutiveOrderNumber) {
        this.consecutiveOrderNumber = consecutiveOrderNumber;
    }

    public List<Expenses> getYearlyExpenses() {
        return yearlyExpenses;
    }

    public void setYearlyExpenses(List<Expenses> yearlyExpenses) {
        this.yearlyExpenses = yearlyExpenses;
    }

    public BigDecimal getProductSoldValue() {
        return productSoldValue;
    }

    public void setProductSoldValue(BigDecimal productSoldValue) {
        this.productSoldValue = productSoldValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("consecutiveOrderNumber", consecutiveOrderNumber)
                .append("yearlyExpenses", yearlyExpenses)
                .append("productSoldValue", productSoldValue)
                .toString();
    }
}
