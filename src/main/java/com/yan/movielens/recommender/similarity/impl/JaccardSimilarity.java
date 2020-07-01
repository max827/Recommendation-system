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
package com.yan.movielens.recommender.similarity.impl;




import com.yan.movielens.recommender.data.DataModel;
import com.yan.movielens.recommender.similarity.AbstractRecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 杰卡德相似度
 *
 */
public class JaccardSimilarity extends AbstractRecommenderSimilarity {


    public JaccardSimilarity(Matrix train) {
        super(train);
    }

    @Override
    public void updateSimilarity(Integer userId,Integer itemId,Double perference){
        Vector items=train.row(userId);
        if(items.contains(itemId)){
            return;
        }
        super.updateSimilarity(userId,itemId,perference);
    }

    @Override
    public double getCorrelation(Integer thisItem, Integer thatItem) {
        //用来记录两个物品的所有用户
        Vector thisVector = train.column(thisItem);
        Vector thatVector = train.column(thatItem);

        Set<Integer> elements = new HashSet<Integer>();

        for(Integer index: thisVector.indexSet()){
            elements.add(index);
        }

        for(Integer index: thatVector.indexSet()){
            elements.add(index);
        }

        int numAllElements = elements.size();
        int numCommonElements = thisVector.size() + thatVector.size() - numAllElements;

        return (numCommonElements + 0.0) / numAllElements;
    }

    protected double getSimilarity(List<? extends Number> thisList, List<? extends Number> thatList) {
        return 0.0;
    }
}
