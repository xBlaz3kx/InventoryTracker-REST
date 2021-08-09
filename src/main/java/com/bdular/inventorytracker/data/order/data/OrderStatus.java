package com.bdular.inventorytracker.data.order.data;

public enum OrderStatus {
    SUBMITTED(0), PROCESSED(1), FULFILLED(2), CONSIGNED(3), PAID(4), CANCELLED(5), CHANGED(6);
    int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
