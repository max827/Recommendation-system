package com.yan.movielens.recommender.recommend;



import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.structure.Matrix;

import java.util.List;

/**
 * 生成推荐结果
 */
public interface Recommender {
    /**
     * 训练推荐模型
     *
     */
    void train() ;

    /**
     * 更新推荐列表
     */
    void updaterRecommendList(Integer userId,Integer itemId,Double perference);

    /**
     * 获得推荐列表（未排序）
     * @return 为排序的推荐列表
     */
    List<RecommendedItem> recommendList();

    /**
     * 获得推荐列表（根据相似度排序）
     * @return 排序好的推荐列表
     */
    List<RecommendedItem> recommendRank();

    /**
     * 获取前n个推荐结果
     * @return
     */
    List<RecommendedItem> recommendTopN(int n);

}
