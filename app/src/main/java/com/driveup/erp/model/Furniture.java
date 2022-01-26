package com.driveup.erp.model;

public class Furniture {
    private String intitule_m;
    private String detail_m;
    private int stock_m;
    private int buying_price_m;

    public Furniture(){}

    public int getBuying_price_m() {
        return buying_price_m;
    }

    public void setBuying_price_m(int buying_price_m) {
        this.buying_price_m = buying_price_m;
    }

    public String getIntitule_m() {
        return intitule_m;
    }

    public void setIntitule_m(String intitule_m) {
        this.intitule_m = intitule_m;
    }

    public String getDetail_m() {
        return detail_m;
    }

    public void setDetail_m(String detail_m) {
        this.detail_m = detail_m;
    }

    public int getStock_m() {
        return stock_m;
    }

    public void setStock_m(int stock_m) {
        this.stock_m = stock_m;
    }
}
