package com.learn.usbank.constant;

import java.util.List;

public final class StackConstant {
    public static final String INR="INR";
    public static final String BASIC_URL="/v1.0";
    public static final String DEFAULT_ZONE_ID="Asia/Kolkata";
    public static final List<String> EXCLUDE_FIELD = List.of("stockId", "symbol", "companyName", "sector", "exchange", "currency");
    public static final String STOCK_EXCEPTION_MESSAGE="Exception occurred while saving Stock :";
    public static final String STOCK_NOT_FOUND_MESSAGE="No stock is available for the given symbol : ";
    public static final String SUCCESSFULLY_DELETED_MESSAGE="Stock has been successfully deleted from the system.";
    public static final String STOCK_EXCEPTION_MESSAGE_PLACEHOLDER ="Exception occurred while processing stock into db : ";
}
