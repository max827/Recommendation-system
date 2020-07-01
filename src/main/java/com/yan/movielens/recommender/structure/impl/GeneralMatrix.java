package com.yan.movielens.recommender.structure.impl;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;


import java.util.Set;

public class GeneralMatrix implements Matrix {

    // 用来存储矩阵的数据结构
    Table<Integer, Integer, Double> data;

    public GeneralMatrix(){
        data = HashBasedTable.create();
    }

    @Override
    public Vector row(int row) {
        return new VectorImpl(data.row(row));
    }

    @Override
    public Vector column(int column) {
        return new VectorImpl(data.column(column));
    }

    @Override
    public double get(int row, int column) {
        return data.get(row, column);
    }

    @Override
    public void set(int row, int column, double value) {
        data.put(row,column,value);
    }

    @Override
    public Set<Integer> columnIndex() {
        return data.columnKeySet();
    }

    @Override
    public Set<Integer> rowIndex() {
        return data.rowKeySet();
    }

    @Override
    public boolean contains(int row, int column) {
        return data.contains(row,column);
    }

    @Override
    public int size() {
        return data.size();
    }
}
