package com.driveup.erp.model;

public class LignCommand {
    private int id_lign_cmd;
    private String product_cmd;
    private int quantity_cmd;
    private int total_price_cmd;

    public LignCommand(){}

    public LignCommand(int id_lign_cmd, String product_cmd, int quantity_cmd, int total_price_cmd) {
        this.id_lign_cmd = id_lign_cmd;
        this.product_cmd = product_cmd;
        this.quantity_cmd = quantity_cmd;
        this.total_price_cmd = total_price_cmd;
    }

    public int getId_lign_cmd() {
        return id_lign_cmd;
    }

    public void setId_lign_cmd(int id_lign_cmd) {
        this.id_lign_cmd = id_lign_cmd;
    }

    public String getProduct_cmd() {
        return product_cmd;
    }

    public void setProduct_cmd(String product_cmd) {
        this.product_cmd = product_cmd;
    }

    public int getQuantity_cmd() {
        return quantity_cmd;
    }

    public void setQuantity_cmd(int quantity_cmd) {
        this.quantity_cmd = quantity_cmd;
    }

    public int getTotal_price_cmd() {
        return total_price_cmd;
    }

    public void setTotal_price_cmd(int total_price_cmd) {
        this.total_price_cmd = total_price_cmd;
    }
}
