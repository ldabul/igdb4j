package com.igdb.constant;

import org.apache.commons.lang3.StringUtils;

public enum OperationCnst implements GeneralCnst<String> {
    EQ,
    GT,
    GTE,
    LT,
    LTE,
    PREFIX,
    EXISTS;

    @Override
    public String getCode() {
        return StringUtils.lowerCase(name());
    }
}
