package com.yan.movielens.recommender.similarity.impl;


import com.yan.movielens.recommender.data.DataModel;
import com.yan.movielens.recommender.similarity.AbstractRecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 同现相似度
 */
public class Co_occurrenceSimilarity extends AbstractRecommenderSimilarity {


    public Co_occurrenceSimilarity(Matrix train) {
        super(train);
    }

    public double getCorrelation(Integer thisItem, Integer thatItem) {
        //用来记录两个物品的所有用户
        Vector thisVector = train.column(thisItem);
        Vector thatVector = train.column(thatItem);

        // compute similarity
        Set<Integer> elements = new HashSet<Integer>();

        for(Integer index: thisVector.indexSet()){
            elements.add(index);
        }

        for(Integer index: thatVector.indexSet()){
            elements.add(index);
        }

        int numAllElements = elements.size();
        int numCommonElements = thisVector.size() + thatVector.size() - numAllElements;

        return (numCommonElements + 0.0) / Math.sqrt( thisVector.size()*thatVector.size());
    }

    protected double getSimilarity(List<? extends Number> thisList, List<? extends Number> thatList) {
        return 0.0;
    }

    @Override
    public void updateSimilarity(Integer userId,Integer itemId,Double perference){
        Vector items=train.row(userId);
        if(items.contains(itemId)){
            return;
        }
        super.updateSimilarity(userId,itemId,perference);
    }
}
