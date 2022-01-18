package com.kpi.payments.domain.sorting;

public enum Direction {
    ASC,
    DESC;

    public static Direction customValueOf(String value) {
        try {
            return Direction.valueOf(value.toUpperCase());
        } catch (NullPointerException exception) {
            return ASC;
        }
    }
}
