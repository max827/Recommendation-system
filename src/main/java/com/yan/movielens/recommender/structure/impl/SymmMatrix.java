/**
 * Copyright (C) 2016 LibRec
 * <p>
 * This file is part of LibRec.
 * LibRec is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * LibRec is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with LibRec. If not, see <http://www.gnu.org/licenses/>.
 */
package com.yan.movielens.recommender.structure.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;
import com.yan.movielens.recommender.util.MapUtils;


import java.util.Set;

/**
 *
 */
public class SymmMatrix implements Matrix {


    // 用来存储矩阵的数据结构
    Table<Integer, Integer, Double> data;


    public SymmMatrix() {
        data = HashBasedTable.create();
    }

    /**
     * 通过外部给的矩阵复制一个相同的矩阵
     * @param mat 外部矩阵
     */
    public SymmMatrix(SymmMatrix mat) {
        data = HashBasedTable.create(mat.data);
    }

    @Override
    public SymmMatrix clone() {
        return new SymmMatrix(this);
    }

    /**
     * 通过行索引和列索引获取对应的值
     * @param row 行索引
     * @param col 列索引
     * @return 对应的值
     */
    @Override
    public double get(int row, int col) {

        if (data.contains(row, col))
            return data.get(row, col);
        else if (data.contains(col, row))
            return data.get(col, row);

        return 0.0d;
    }

    /**
     * 根据行索引和列索引判断这个位置是否存在值
     * @param row 行索引
     * @param col 列索引
     * @return 是否存在值
     */
    public boolean contains(int row, int col) {
        return data.contains(row, col) || data.contains(col, row);
    }

    @Override
    public int size() {
        return data.size();
    }

    /**
     * 在指定位置设置值
     * @param row 行索引
     * @param col 列索引
     * @param val 值
     */
    @Override
    public void set(int row, int col, double val) {
        if (row >= col)
            data.put(row, col, val);
        else
            data.put(col, row, val);
    }

    /**
     * 获取所有的列索引
     * @return 所有列索引
     */
    @Override
    public Set<Integer> columnIndex() {
        return data.columnKeySet();
    }

    /**
     * 获取所有的行索引
     * @return 所有行索引
     */
    @Override
    public Set<Integer> rowIndex() {
        return data.rowKeySet();
    }

    /**
     * 给指定的位置的值加上传进来的值
     * @param row 行索引
     * @param col 列索引
     * @param val 需要加上的值
     */
    public void add(int row, int col, double val) {
        if (row >= col)
            data.put(row, col, val + get(row, col));
        else
            data.put(col, row, val + get(col, row));
    }



    /**
     * 获取行向量
     *
     * @param row 行索引
     * @return 行向量
     */
    @Override
    public Vector row(int row) {
        return new VectorImpl(MapUtils.combineMap(data.row(row),data.column(row)));
    }

    /**
     * 根据列索引获取列向量
     *
     * @param column 列索引
     * @return 列向量
     */
    @Override
    public Vector column(int column) {
        return row(column);
    }



    /**
     * @return 这个矩阵
     */
    public Table<Integer, Integer, Double> getMatrix() {
        return data;
    }


}
