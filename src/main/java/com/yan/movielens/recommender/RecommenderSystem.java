package com.yan.movielens.recommender;



import com.yan.movielens.recommender.recommend.RecommenderContext;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.similarity.RecommenderSimilarity;

import java.util.List;


public interface RecommenderSystem {
    void similarityInit();
    void recommendInit();
    void updateRecList(Integer userId,Integer itemId,Double perference);

    RecommenderContext getRecommenderContext();
    void setRecommenderContext(RecommenderContext recommenderContext);
    List<RecommendedItem> getRecommendedItemList();
    //设置推荐系统的两个参数
    void setK(int k);
    void serN(int n);
}
