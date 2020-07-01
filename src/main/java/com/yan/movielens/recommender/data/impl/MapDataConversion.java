package com.yan.movielens.recommender.data.impl;


import com.yan.movielens.recommender.data.AbstractDataModel;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.impl.GeneralMatrix;


import java.util.Map;

public class MapDataConversion extends AbstractDataModel {

    Map<Integer, Map<Integer,Double>> train;

    public MapDataConversion(Map<Integer, Map<Integer,Double>> train){
        this.train=train;
        super.data= (Matrix) new GeneralMatrix();
        dataConversion();
    }

    public void dataConversion(){

        for(Map.Entry<Integer,Map<Integer,Double>> entry:train.entrySet()){
            Map<Integer,Double> map=entry.getValue();
            for(Map.Entry<Integer,Double> entry1:map.entrySet()){
                super.data.set(entry.getKey(),entry1.getKey(),entry1.getValue());
            }
        }
    }

}
