package com.naufalzaul.openmarketid.constant;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PAID("paid"),
    NOT_PAID("not_paid"),
    CANCELLED("cancelled");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }
}
