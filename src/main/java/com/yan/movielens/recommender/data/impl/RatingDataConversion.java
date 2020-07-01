package com.yan.movielens.recommender.data.impl;

import com.yan.movielens.entity.Rating;
import com.yan.movielens.recommender.data.AbstractDataModel;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.impl.GeneralMatrix;

import java.util.List;

public class RatingDataConversion extends AbstractDataModel {


    List<Rating> ratingList;

    public RatingDataConversion(List<Rating> ratingList){
        this.ratingList=ratingList;
        super.data= (Matrix) new GeneralMatrix();
        dataConversion();
    }

    public void dataConversion(){

        for(Rating rating:ratingList){
            Integer userId=rating.getKey().getUserId();
            Integer movieId=rating.getKey().getMovieId();
            Double rate=rating.getRating();
            super.data.set(userId,movieId,rate);
        }
    }

}
