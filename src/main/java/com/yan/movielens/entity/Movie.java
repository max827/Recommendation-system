package com.yan.movielens.entity;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title",columnDefinition = "varchar(100) not null")
    private String title;

    @Column(name = "genres",columnDefinition = "varchar(100) not null")
    private String genres;
}
