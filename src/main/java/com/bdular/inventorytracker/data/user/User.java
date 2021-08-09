package com.bdular.inventorytracker.data.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.*;

public class User {

    @MongoId
    private String ID;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String address;
    @NotEmpty
    private String post;
    @Email
    private String email;
    @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
            "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
            "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
            "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
            "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
            "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
            "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
            "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$", message = "VAT ID is not a valid.")
    private String VAT_ID;

    @NotNull
    boolean isSuperUser;

    public User(@NotEmpty String name, @NotEmpty String surname, @NotEmpty String address, @NotEmpty String post, @Email String email, @Size(min = 8, max = 10) @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
            "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
            "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
            "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
            "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
            "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
            "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
            "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$", message = "VAT ID is not a valid.") String VAT_ID, @NotNull boolean isSuperUser) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.post = post;
        this.email = email;
        this.VAT_ID = VAT_ID;
        this.isSuperUser = isSuperUser;
    }

    public User(@NotEmpty String name, @NotEmpty String surname, @NotEmpty String address, @NotEmpty String post, @Size(min = 8, max = 10) @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
            "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
            "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
            "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
            "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
            "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
            "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
            "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$", message = "VAT ID is not a valid.") String VAT_ID, @NotNull boolean isSuperUser) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.post = post;
        this.VAT_ID = VAT_ID;
        this.isSuperUser = isSuperUser;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getVAT_ID() {
        return VAT_ID;
    }

    public void setVAT_ID(String VAT_ID) {
        this.VAT_ID = VAT_ID;
    }

    public boolean isSuperUser() {
        return isSuperUser;
    }

    public void setSuperUser(boolean superUser) {
        isSuperUser = superUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ID", ID)
                .append("name", name)
                .append("surname", surname)
                .append("address", address)
                .append("post", post)
                .append("email", email)
                .append("VAT_ID", VAT_ID)
                .append("isSuperUser", isSuperUser)
                .toString();
    }
}
