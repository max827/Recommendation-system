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
package com.yan.movielens.recommender.recommend;


import com.yan.movielens.recommender.similarity.RecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;

/**
 * 推荐系统上下文
 *
 */
public class RecommenderContext {

    protected Matrix trainData;
    protected RecommenderSimilarity similarity;
    protected Integer userId;

    public RecommenderContext(Matrix trainData, RecommenderSimilarity similarity,Integer userId) {
        this.trainData = trainData;
        this.similarity = similarity;
        this.userId = userId;
    }
    public RecommenderContext(){}


    public Matrix getTrainData() {
        return trainData;
    }

    public RecommenderSimilarity getSimilarity() {
        return similarity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setSimilarity(RecommenderSimilarity similarity) {
        this.similarity = similarity;
    }

    public void setTrainData(Matrix trainData){
        this.trainData=trainData;;
    }
}
