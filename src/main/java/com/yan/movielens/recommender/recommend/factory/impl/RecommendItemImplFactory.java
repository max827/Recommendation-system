package com.yan.movielens.recommender.recommend.factory.impl;




import com.yan.movielens.recommender.recommend.factory.RecommendItemFactory;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.recommend.item.impl.RecommendedItemImpl;

import java.util.HashMap;

public class RecommendItemImplFactory implements RecommendItemFactory {
    @Override
    public RecommendedItem createRecommendItem(int id) {
        return new RecommendedItemImpl(id,new HashMap<Integer, Double>());
    }
}
