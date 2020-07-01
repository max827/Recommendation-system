package com.yan.movielens.init;


import com.yan.movielens.entity.Rating;
import com.yan.movielens.recommender.data.DataModel;
import com.yan.movielens.recommender.data.impl.RatingDataConversion;
import com.yan.movielens.recommender.similarity.RecommenderSimilarity;
import com.yan.movielens.recommender.similarity.impl.ExCo_occurrenceSimilarity;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.repository.RatingRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

@Component
public class InitSystem implements ServletContextListener, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private RecommenderSimilarity recommenderSimilarity;
    private List<Rating> ratingList;
    private Matrix train = null;
    private DataModel dataModel;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loadData(sce.getServletContext());
    }

    /**
     * 加载数据到application缓存中
     * @param context servlet上下文
     */
    public void loadData(ServletContext context) {
        //获取数据
        RatingRepository ratingRepository=applicationContext.getBean(RatingRepository.class);
//        ratingList=ratingRepository.findAll();
        ratingList=ratingRepository.getRandomList(500);
        dataModel=new RatingDataConversion(ratingList);
        train=dataModel.getData();
        recommenderSimilarity=new ExCo_occurrenceSimilarity(train);
        if(recommenderSimilarity.getSim().size()==0) {
            long startTime = System.currentTimeMillis();

            recommenderSimilarity.simialarityInit();

            long endTime = System.currentTimeMillis();
            long runTime=endTime-startTime;
            System.out.println("相似度矩阵初始化时间为："+runTime+"ms");
        }
    }

    public RecommenderSimilarity getSimilarity(){
        return recommenderSimilarity;
    }

    public Matrix getTrain(){
        return train;
    };

}

