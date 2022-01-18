package com.kpi.payments;

import com.kpi.payments.domain.sorting.Direction;
import com.kpi.payments.domain.sorting.SortBy;

import java.util.Arrays;

public class TestClass {

    public static void main(String[] args) {
        System.out.println(SortBy.customValueOf("Id"));
        System.out.println(SortBy.customValueOf("Name"));
        System.out.println(SortBy.customValueOf("Money amount"));
        System.out.println(Direction.customValueOf("ASC"));
        System.out.println(Direction.customValueOf("DESC"));
        System.out.println(Direction.customValueOf("asc"));
        System.out.println(Direction.customValueOf("desc"));
    }
}
