package com.yan.movielens.recommender.similarity;


import com.yan.movielens.recommender.structure.Matrix;


public interface RecommenderSimilarity {
    /**
     * 相似度矩阵初始化
     */
    void simialarityInit();

    /**
     * 更新相似度矩阵
     */
    void updateSimilarity(Integer userId,Integer itemId,Double perference);

    /**
     * 得到相似度矩阵
     * @return
     */
    Matrix getSim();

    /**
     * 得到训练集
     * @return
     */
    Matrix getTrain();

}
