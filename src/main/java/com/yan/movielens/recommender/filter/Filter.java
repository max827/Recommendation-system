package com.yan.movielens.recommender.filter;

import com.yan.movielens.recommender.recommend.item.RecommendedItem;

import java.util.List;

public interface Filter {
    /**
     * 进行过滤
     */
    void filter();

    /**
     * 获得过滤后的推荐列表
     * @param num 最后返回的推荐列表中应该有num个推荐物品
     * @return 过滤后的推荐列表
     */
    List<RecommendedItem> getRecommendedItemList(int num);
}
