package com.driveup.erp.model;

import com.google.firebase.database.ServerValue;

public class Command {

    private String code_cmd;
    private String customer_cmd;
    private String status_cmd;
    private Object delivery_dt_cmd;
    private Object order_dt_cmd;
    private LignCommand lignCommand;

    public Command(){}

    public Command(String code_cmd, String customer_cmd, String status_cmd, LignCommand lignCommand) {
        this.code_cmd = code_cmd;
        this.customer_cmd = customer_cmd;
        this.status_cmd = status_cmd;
        this.delivery_dt_cmd = ServerValue.TIMESTAMP;
        this.order_dt_cmd = ServerValue.TIMESTAMP;
        this.lignCommand = lignCommand;
    }

    public Command(String code_cmd, String customer_cmd, String status_cmd, Object delivery_dt_cmd, Object order_dt_cmd, LignCommand lignCommand) {
        this.code_cmd = code_cmd;
        this.customer_cmd = customer_cmd;
        this.status_cmd = status_cmd;
        this.delivery_dt_cmd = delivery_dt_cmd;
        this.order_dt_cmd = order_dt_cmd;
        this.lignCommand = lignCommand;
    }

    public String getCode_cmd() {
        return code_cmd;
    }

    public void setCode_cmd(String code_cmd) {
        this.code_cmd = code_cmd;
    }

    public String getCustomer_cmd() {
        return customer_cmd;
    }

    public void setCustomer_cmd(String customer_cmd) {
        this.customer_cmd = customer_cmd;
    }

    public String getStatus_cmd() {
        return status_cmd;
    }

    public void setStatus_cmd(String status_cmd) {
        this.status_cmd = status_cmd;
    }

    public Object getDelivery_dt_cmd() {
        return delivery_dt_cmd;
    }

    public void setDelivery_dt_cmd(Object delivery_dt_cmd) {
        this.delivery_dt_cmd = delivery_dt_cmd;
    }

    public Object getOrder_dt_cmd() {
        return order_dt_cmd;
    }

    public void setOrder_dt_cmd(Object order_dt_cmd) {
        this.order_dt_cmd = order_dt_cmd;
    }

    public LignCommand getLignCommand() {
        return lignCommand;
    }

    public void setLignCommand(LignCommand lignCommand) {
        this.lignCommand = lignCommand;
    }
}
