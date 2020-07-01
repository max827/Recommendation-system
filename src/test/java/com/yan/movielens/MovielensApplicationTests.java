package com.yan.movielens;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.Rating;
import com.yan.movielens.entity.User;
import com.yan.movielens.entity.key.UserAndMovieKey;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.recommender.data.DataModel;
import com.yan.movielens.recommender.data.impl.RatingDataConversion;
import com.yan.movielens.recommender.evaluation.Metric;
import com.yan.movielens.recommender.evaluation.NewMetric;
import com.yan.movielens.recommender.recommend.Recommender;
import com.yan.movielens.recommender.recommend.RecommenderContext;
import com.yan.movielens.recommender.recommend.factory.impl.RecommendItemImplFactory;
import com.yan.movielens.recommender.recommend.impl.ItemCFRecommender;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.similarity.RecommenderSimilarity;
import com.yan.movielens.recommender.similarity.impl.*;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.repository.CollectionRepository;
import com.yan.movielens.repository.RatingRepository;
import com.yan.movielens.service.*;
import com.yan.movielens.util.CommonUtils;
import com.yan.movielens.util.MapUtils;
import org.hibernate.sql.OracleJoinFragment;
import org.hibernate.validator.constraints.EAN;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;

import java.util.*;

@SpringBootTest
class MovielensApplicationTests {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecMovieService recMovieService;
    @Autowired
    private RatingRepository ratingRepository;

    @Test
    void contextLoads() {
        List<Integer> collectionList=collectionRepository.getCollectionListById(2);
        for(Integer a:collectionList){
            System.out.println(a);
        }
    }

    /**
     * 测试不同的k和n对评价指标有什么影响
     */
    @Test
    void kAndnTest(){
        DataModel dataModel=new RatingDataConversion(ratingRepository.findAll());
        dataModel.cuttingData(200);
        dataModel.splitData(dataModel,5);
        Matrix train=dataModel.getTrainData();
        Matrix test=dataModel.getTestData();


        System.out.println("同现相似度:");
        for(int k=10;k<100;k=k+10){
            System.out.println("n:"+k);
            metric(new Co_occurrenceSimilarity(train),train,test,k,10);
            System.out.println("-------------------");

        }

    }

    /**
     * 测试各种不同的相似度对评价指标的影响
     */
    @Test
    void algorithmToCompare(){
        DataModel dataModel=new RatingDataConversion(ratingRepository.findAll());
        dataModel.cuttingData(200);
        dataModel.splitData(dataModel,5);
        Matrix train=dataModel.getTrainData();
        Matrix test=dataModel.getTestData();

//
//        System.out.println("同现相似度:");
//        metric(new Co_occurrenceSimilarity(train),train,test,10,10);
//        System.out.println("-------------------");
//
        System.out.println("欧氏距离相似度:");
        metric(new EuclideanDistanceSimilarity(train),train,test,10,10);
        System.out.println("-------------------");
//
//        System.out.println("扩展同现相似度:");
//        metric(new ExCo_occurrenceSimilarity(train),train,test,10,10);
//        System.out.println("-------------------");
//
//        System.out.println("杰卡德相似度:");
//        metric(new JaccardSimilarity(train),train,test,10,10);
//        System.out.println("-------------------");
//
//        System.out.println("余弦相似度:");
//        metric(new CosineSimilarity(train),train,test,10,10);
//        System.out.println("-------------------");
//
//        System.out.println("修正的余弦相似度:");
//        metric(new ExCosineSimilarity(train),train,test,10,10);
//        System.out.println("-------------------");
//
        System.out.println("皮尔逊相似度:");
        metric(new PCCSimilarity(train),train,test,10,10);
        System.out.println("-------------------");
//
//        System.out.println("扩展杰卡德相似度:");
//        metric(new ExJaccardSimilarity(train),train,test,10,10);
//        System.out.println("-------------------");
//
//        System.out.println("骰子系数相似度:");
//        metric(new DiceCoefficientSimilarity(train),train,test);
//        System.out.println("-------------------");
//
//        System.out.println("均方差相似度:");
//        metric(new MSDSimilarity(train),train,test);
//        System.out.println("-------------------");
//
//        System.out.println("均方误差相似度:");
//        metric(new MSESimilarity(train),train,test);
//        System.out.println("-------------------");
    }

    private void metric(RecommenderSimilarity similarity, Matrix train, Matrix test,int n,int k){
        long startTime = System.currentTimeMillis();

        NewMetric newMetric=new NewMetric(n,k,train,test,similarity);
        newMetric.result();

        long endTime2 = System.currentTimeMillis();
        System.out.println("此次计算时间：" + (endTime2 - startTime) + "ms");
    }

    @Test
    public void splitListTest(){

    }

    /**
     * 模拟数据测试算法
     */
    @Test
    public void simulation(){
        List<Rating> ratings=new ArrayList<>();
        ratings.add(new Rating(1,1,3.0, (long) 123));
        ratings.add(new Rating(1,2,1.0, (long) 123));
        ratings.add(new Rating(1,4,4.0, (long) 123));
        ratings.add(new Rating(2,1,4.0, (long) 123));
        ratings.add(new Rating(2,2,2.0, (long) 123));
        ratings.add(new Rating(2,4,5.0, (long) 123));
        ratings.add(new Rating(3,1,5.0, (long) 123));
        ratings.add(new Rating(3,3,3.0, (long) 123));

        //数据转换
        DataModel dataModel=new RatingDataConversion(ratings);
        Matrix train=dataModel.getData();
        //相似度计算
        RecommenderSimilarity recommenderSimilarity=new CosineSimilarity(train);
        recommenderSimilarity.simialarityInit();
        Matrix sim=recommenderSimilarity.getSim();
        System.out.println(sim.get(1,2));
        //获得推荐列表
//        RecommenderContext recommenderContext=new RecommenderContext(train,recommenderSimilarity,1);
//        Recommender recommender=new ItemCFRecommender(recommenderContext,new RecommendItemImplFactory(),2);
//        recommender.train();
//        List<RecommendedItem> recommendedItemList = recommender.recommendTopN(2);
    }
}
