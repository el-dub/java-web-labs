package com.kpi.payments.domain.sorting;

public enum SortBy {
    ID,
    NAME,
    DATE,
    MONEY_AMOUNT;

    public static SortBy customValueOf(String value) {
        try {
            return SortBy.valueOf(value.toUpperCase());
        } catch (NullPointerException exception) {
            return ID;
        }
    }
}
