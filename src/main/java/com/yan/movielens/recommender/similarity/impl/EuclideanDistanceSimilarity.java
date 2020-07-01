package com.yan.movielens.recommender.similarity.impl;

import com.yan.movielens.recommender.similarity.AbstractRecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;

import java.util.List;

/**
 * 欧氏距离计算相似度
 */
public class EuclideanDistanceSimilarity extends AbstractRecommenderSimilarity {
    public EuclideanDistanceSimilarity(Matrix train) {
        super(train);
    }

    @Override
    protected double getSimilarity(List<? extends Number> thisList, List<? extends Number> thatList) {

        double distance=0;
        for(int i=0;i<thisList.size();i++){
            double subtraction=thisList.get(i).doubleValue()-thatList.get(i).doubleValue();
            distance += subtraction*subtraction;
        }
        return 1/(1+distance);
    }
}
