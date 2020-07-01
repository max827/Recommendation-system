package com.yan.movielens.recommender.structure.impl;




import com.yan.movielens.recommender.structure.Vector;

import java.util.Map;
import java.util.Set;

public class VectorImpl implements Vector {

    Map<Integer,Double> vector;

    public VectorImpl(Map<Integer,Double> vector){
        this.vector = vector;
    }

    /**
     * 获取这个向量的大小
     * @return 向量的大小
     */
    @Override
    public int size() {
        return vector.size();
    }

    /**
     * 根据索引获取向量中具体的值
     * @param index 索引，这里可以理解为物品或者用户的ID
     * @return 向量中的值
     */
    @Override
    public double get(int index) {
        return vector.get(index);
    }

    /**
     * 对向量进行赋值
     * @param index 索引
     * @param value 值
     */
    @Override
    public void set(int index, double value) {
        vector.put(index,value);
    }

    @Override
    public boolean contains(int index) {
        return vector.get(index)!=null;
    }

    @Override
    public void remove(int index) {
        vector.remove(index);
    }

    @Override
    public void clear() {
        vector.clear();
    }

    @Override
    public Set<Integer> indexSet() {
        return vector.keySet();
    }

    @Override
    public Map<Integer, Double> returnMap() {
        return vector;
    }


}
