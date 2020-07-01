package com.yan.movielens.service.impl;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.MovieManagement;
import com.yan.movielens.entity.Rating;
import com.yan.movielens.recommender.RecommenderSystem;
import com.yan.movielens.recommender.RecommenderSystemImpl;
import com.yan.movielens.recommender.data.DataModel;
import com.yan.movielens.recommender.data.impl.RatingDataConversion;
import com.yan.movielens.recommender.recommend.Recommender;
import com.yan.movielens.recommender.recommend.RecommenderContext;
import com.yan.movielens.recommender.recommend.factory.impl.RecommendItemImplFactory;
import com.yan.movielens.recommender.recommend.impl.ItemCFRecommender;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.similarity.RecommenderSimilarity;
import com.yan.movielens.recommender.similarity.impl.ExCo_occurrenceSimilarity;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecMovieServiceImplTest {

    @Autowired
    private RatingRepository ratingRepository;

    private List<RecommendedItem> recommendedItemList;
    private RecommenderSimilarity recommenderSimilarity;
    private List<Rating> ratingList;
    private Matrix train = null;
    private DataModel dataModel;
    private RecommenderSystem recommenderSystem;
    @Test
    void test(){
        //获得数据
//        ratingList=ratingRepository.findAll();
        ratingList=ratingRepository.getRandomList(1000);
        //转换数据
        dataModel=new RatingDataConversion(ratingList);
        train=dataModel.getData();

        //计算相似度矩阵
        recommenderSimilarity=new ExCo_occurrenceSimilarity(train);
        if(recommenderSimilarity.getSim().size()==0) {
            recommenderSimilarity.simialarityInit();
        }

       //计算推荐列表
        RecommenderContext recommenderContext=new RecommenderContext(train,
                                                                     recommenderSimilarity,
                                                                    10);
        Recommender recommender=new ItemCFRecommender(recommenderContext,new RecommendItemImplFactory(),3);
        recommender.train();
        recommendedItemList = recommender.recommendTopN(5);

        int reason=0;
        for(RecommendedItem recommendedItem:recommendedItemList){
            Integer movieId=recommendedItem.getItemId();
            reason=recommendedItem.getTrueReason();
            System.out.print(" 电影："+movieId+" 原因："+reason+"|");
        }


        //进行更新
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println();


        double perference=train.get(10,reason);
        recommender.updaterRecommendList(10,reason,perference*0.75);
        recommendedItemList = recommender.recommendTopN(5);
        System.out.println("更新后的推荐列表------------------");
        for(RecommendedItem recommendedItem:recommendedItemList){
            Integer movieId=recommendedItem.getItemId();
            Integer reason1=recommendedItem.getTrueReason();
            System.out.print(" 电影："+movieId+" 原因："+reason1+"|");
        }
        System.out.println();
    }
}