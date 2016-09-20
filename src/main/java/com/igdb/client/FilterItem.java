package com.igdb.client;

import org.apache.commons.lang3.StringUtils;

import com.igdb.constant.OperationCnst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterItem {
    private String field;
    private Object value;
    private OperationCnst operation;

    @Override
    public String toString() {
        if (value == null || StringUtils.isBlank(String.valueOf(value))) {
            return StringUtils.EMPTY;
        }
        return String.format("filter[%s][%s]=%s",
                             field,
                             operation.getCode(),
                             String.valueOf(value));
    }

    public static FilterItem eq(String field, Object value) {
        return of(field,
                  value,
                  OperationCnst.EQ);
    }

    public static FilterItem gt(String field, Object value) {
        return of(field,
                  value,
                  OperationCnst.GT);
    }

    public static FilterItem gte(String field, Object value) {
        return of(field,
                  value,
                  OperationCnst.GTE);
    }

    public static FilterItem lt(String field, Object value) {
        return of(field,
                  value,
                  OperationCnst.LT);
    }

    public static FilterItem lte(String field, Object value) {
        return of(field,
                  value,
                  OperationCnst.LTE);
    }

    public static FilterItem prefix(String field, Object value) {
        return of(field,
                  value,
                  OperationCnst.PREFIX);
    }

    public static FilterItem exists(String field, Object value) {
        return of(field,
                  value,
                  OperationCnst.EXISTS);
    }

    public static FilterItem of(String field, Object value, OperationCnst operation) {
        return new FilterItem(field,
                              value,
                              operation);
    }
}
