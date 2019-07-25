package com.im.demo.notice.config.db;

import org.springframework.util.ObjectUtils;


public class DbContextHolder {

    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(DataSourceType dataSourceType) {
        if (ObjectUtils.isEmpty(dataSourceType)) {
            throw new NullPointerException();
        }
        contextHolder.set(dataSourceType);
    }

    public static DataSourceType getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
