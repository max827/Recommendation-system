package com.yan.movielens.recommender.recommend.factory;


import com.yan.movielens.recommender.recommend.item.RecommendedItem;

public interface RecommendItemFactory {
    /**
     * 创建一个RecommendItem类
     * @return
     */
    public RecommendedItem createRecommendItem(int id);
}
