package com.yan.movielens.entity.model;

import lombok.Data;

import java.util.List;

@Data
public class PageEntity<K> {
    private Integer pageNum;
    private Integer total;
    private List<K> list;
}
