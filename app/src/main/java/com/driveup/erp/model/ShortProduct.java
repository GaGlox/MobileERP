package com.driveup.erp.model;

public class ShortProduct {
    private String format_p;
    private int stock_p;

    public ShortProduct(){}

    public ShortProduct(String format_p, int stock_p) {
        this.format_p = format_p;
        this.stock_p = stock_p;
    }

    public String getFormat_p() {
        return format_p;
    }

    public void setFormat_p(String format_p) {
        this.format_p = format_p;
    }

    public int getStock_p() {
        return stock_p;
    }

    public void setStock_p(int stock_p) {
        this.stock_p = stock_p;
    }
}
