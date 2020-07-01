package com.yan.movielens.recommender;


import com.yan.movielens.recommender.recommend.Recommender;
import com.yan.movielens.recommender.recommend.RecommenderContext;
import com.yan.movielens.recommender.recommend.factory.impl.RecommendItemImplFactory;
import com.yan.movielens.recommender.recommend.impl.ItemCFRecommender;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.similarity.RecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;

import java.util.List;


public class RecommenderSystemImpl implements RecommenderSystem {

    private Integer userId;
    private RecommenderSimilarity similarity;
    private List<RecommendedItem> recommendedItemList=null;
    private RecommenderContext recommenderContext;
    private Recommender recommender;
    private Integer k;
    private Integer n;

    public RecommenderSystemImpl(RecommenderContext recommenderContext,int k,int n){
        this.k=k;
        this.n=n;
        this.recommenderContext=recommenderContext;
        similarity=recommenderContext.getSimilarity();
        userId=recommenderContext.getUserId();
    }

    @Override
    public void similarityInit(){
        //初始化相似度矩阵，前提是之前没有初始化过
        if(similarity.getSim().size()==0) {
            similarity.simialarityInit();
        }
    }

    @Override
    public void recommendInit() {
        //计算推荐结果
        recommender=new ItemCFRecommender(recommenderContext,new RecommendItemImplFactory(),k);
        recommender.train();
        recommendedItemList = recommender.recommendTopN(n);
    }

    @Override
    public void updateRecList(Integer userId,Integer itemId,Double perference){
        updateSimilarity(userId,itemId,perference);
        updateRecommend(userId,itemId,perference);
        recommendedItemList = recommender.recommendTopN(n);
    }
    private void updateSimilarity(Integer userId,Integer itemId,Double perference) {
        similarity.updateSimilarity(userId,itemId,perference);
    }
    private void updateRecommend(Integer userId,Integer itemId,Double perference){
        recommender.updaterRecommendList(userId,itemId,perference);
    }




    @Override
    public List<RecommendedItem> getRecommendedItemList() {
        return recommendedItemList;
    }

    @Override
    public void setK(int k) {
        this.k=k;
    }

    @Override
    public void serN(int n) {
        this.n=n;
    }

    @Override
    public RecommenderContext getRecommenderContext() {
        return recommenderContext;
    }

    @Override
    public void setRecommenderContext(RecommenderContext recommenderContext) {
        this.recommenderContext = recommenderContext;
        similarity=recommenderContext.getSimilarity();
        userId=recommenderContext.getUserId();
    }

}
