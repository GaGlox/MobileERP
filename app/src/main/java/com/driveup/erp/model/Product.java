package com.driveup.erp.model;

public class Product {
    private String intitule_p;
    private String format_p;
    private int stock_p;
    private int pu_p;
    private String color_p;

    public Product(String intitule_p, String format_p, String color_p, int stock_p, int pu_p) {
        this.intitule_p = intitule_p;
        this.format_p = format_p;
        this.color_p = color_p;
        this.stock_p = stock_p;
        this.pu_p = pu_p;
    }

    public String getIntitule_p() {
        return intitule_p;
    }

    public void setIntitule_p(String intitule_p) {
        this.intitule_p = intitule_p;
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

    public int getPu_p() {
        return pu_p;
    }

    public void setPu_p(int pu_p) {
        this.pu_p = pu_p;
    }

    public String getColor_p() {
        return color_p;
    }

    public void setColor_p(String color_p) {
        this.color_p = color_p;
    }
}
