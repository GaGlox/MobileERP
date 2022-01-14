package com.driveup.erp.model;

public class Agent {
    private String name_u;
    private String code_u;
    private String fonction_u;

    public Agent(){}

    public Agent(String name_u, String code_u, String fonction_u) {
        this.name_u = name_u;
        this.code_u = code_u;
        this.fonction_u = fonction_u;
    }

    public String getName_u() {
        return name_u;
    }

    public void setName_u(String name_u) {
        this.name_u = name_u;
    }

    public String getCode_u() {
        return code_u;
    }

    public void setCode_u(String code_u) {
        this.code_u = code_u;
    }

    public String getFonction_u() {
        return fonction_u;
    }

    public void setFonction_u(String fonction_u) {
        this.fonction_u = fonction_u;
    }
}
