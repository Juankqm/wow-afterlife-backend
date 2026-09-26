package com.afterlife.wow_afterlife_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(
            name = "username",
            nullable = false,
            length = 32,
            unique = true
    )
    private String username;

    @Column(
            name = "salt",
            nullable = false,
            columnDefinition = "BINARY(32)"
    )
    private byte[] salt;

    @Column(
            name = "verifier",
            nullable = false,
            columnDefinition = "BINARY(32)"
    )
    private byte[] verifier;

    @Column(
            name = "email",
            nullable = false,
            length = 255
    )
    private String email;

    @Column(
            name = "reg_mail",
            nullable = false,
            length = 255
    )
    private String regMail;

    @Column(name = "expansion")
    private Integer expansion = 2;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getVerifier() {
        return verifier;
    }

    public String getEmail() {
        return email;
    }

    public String getRegMail() {
        return regMail;
    }

    public Integer getExpansion() {
        return expansion;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setVerifier(byte[] verifier) {
        this.verifier = verifier;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegMail(String regMail) {
        this.regMail = regMail;
    }

    public void setExpansion(Integer expansion) {
        this.expansion = expansion;
    }
}
