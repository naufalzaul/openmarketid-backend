package com.naufalzaul.openmarketid.constant;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH("cash"),
    SCAN_QR("scan_qr"),
    CREDIT_CARD("credit_card");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public static PaymentMethod findByMethod(String method) {
        for (PaymentMethod paymentMethod : values()) {
            if (paymentMethod.value.equalsIgnoreCase(method)) {
                return paymentMethod;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + method);
    }
}
