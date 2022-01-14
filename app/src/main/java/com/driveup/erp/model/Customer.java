package com.driveup.erp.model;

public class Customer {
    private String firstname_cl;
    private String lastname_cl;
    private String address_cl;
    private String mail_cl;
    private String phone_cl;

    public Customer(){}

    public Customer(String firstname_cl, String lastname_cl, String address_cl, String mail_cl, String phone_cl) {
        this.firstname_cl = firstname_cl;
        this.lastname_cl = lastname_cl;
        this.address_cl = address_cl;
        this.mail_cl = mail_cl;
        this.phone_cl = phone_cl;
    }

    public String getFirstname_cl() {
        return firstname_cl;
    }

    public void setFirstname_cl(String firstname_cl) {
        this.firstname_cl = firstname_cl;
    }

    public String getLastname_cl() {
        return lastname_cl;
    }

    public void setLastname_cl(String lastname_cl) {
        this.lastname_cl = lastname_cl;
    }

    public String getAddress_cl() {
        return address_cl;
    }

    public void setAddress_cl(String address_cl) {
        this.address_cl = address_cl;
    }

    public String getMail_cl() {
        return mail_cl;
    }

    public void setMail_cl(String mail_cl) {
        this.mail_cl = mail_cl;
    }

    public String getPhone_cl() {
        return phone_cl;
    }

    public void setPhone_cl(String phone_cl) {
        this.phone_cl = phone_cl;
    }
}
