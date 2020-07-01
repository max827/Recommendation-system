package com.yan.movielens.recommender.structure;

import java.util.Set;

public interface Matrix {

    Vector row(int row);

    Vector column(int column);

    double get(int row, int column);

    void set(int row, int column, double value);

    Set<Integer> columnIndex();

    Set<Integer> rowIndex();

    boolean contains(int row, int column);

    int size();
}
