package com.bdular.inventorytracker.data.payment;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static com.bdular.inventorytracker.data.payment.PaymentStatus.AWAITING_PAYMENT;

public class Payment {

    @NotNull
    @PositiveOrZero
    BigDecimal totalAmount;
    BigDecimal amountPaid;
    BigDecimal amountOwed;
    PaymentStatus status;
    @NotEmpty
    ArrayList<PaymentType> paymentTypes;
    HashMap<PaymentType, LocalDateTime> paymentDates;
    HashMap<LocalDateTime, BigDecimal> paymentAmount;
    LocalDateTime paymentDue;

    public Payment(BigDecimal amountPaid, BigDecimal amountOwed, ArrayList<PaymentType> paymentTypes) {
        this.amountPaid = amountPaid;
        this.amountOwed = amountOwed;
        this.status = AWAITING_PAYMENT;
        this.paymentTypes = paymentTypes;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(BigDecimal amountOwed) {
        this.amountOwed = amountOwed;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public ArrayList<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(ArrayList<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public HashMap<PaymentType, LocalDateTime> getPaymentDates() {
        return paymentDates;
    }

    public void setPaymentDates(HashMap<PaymentType, LocalDateTime> paymentDates) {
        this.paymentDates = paymentDates;
    }

    public LocalDateTime getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(LocalDateTime paymentDue) {
        this.paymentDue = paymentDue;
    }

    public HashMap<LocalDateTime, BigDecimal> getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(HashMap<LocalDateTime, BigDecimal> paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("totalAmount", totalAmount)
                .append("amountPaid", amountPaid)
                .append("amountOwed", amountOwed)
                .append("status", status)
                .append("paymentTypes", paymentTypes)
                .append("paymentDates", paymentDates)
                .append("paymentAmount", paymentAmount)
                .append("paymentDue", paymentDue)
                .toString();
    }
}
