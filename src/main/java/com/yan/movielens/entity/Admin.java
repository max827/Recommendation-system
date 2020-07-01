package com.yan.movielens.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", columnDefinition = "varchar(50) not null")
    private String adminname;

    @Column(name = "password", columnDefinition = "varchar(255) not null")
    private String password;
}
