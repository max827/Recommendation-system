package com.yan.movielens.recommender.data;


import com.yan.movielens.recommender.structure.Matrix;

public interface DataModel {
    Matrix getTrainData();

    Matrix getData();

    Matrix getTestData();

    void splitData(DataModel data,int blocknum);

    void cuttingData(int num);

    void setData(Matrix data);
}
