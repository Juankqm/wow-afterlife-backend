package com.afterlife.wow_afterlife_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}