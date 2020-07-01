package com.yan.movielens.service.impl;


import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.Rating;
import com.yan.movielens.entity.key.UserAndMovieKey;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.RecommendEntity;
import com.yan.movielens.init.InitSystem;
import com.yan.movielens.recommender.RecommenderSystem;
import com.yan.movielens.recommender.RecommenderSystemImpl;
import com.yan.movielens.recommender.recommend.RecommenderContext;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;
import com.yan.movielens.repository.MovieRepository;
import com.yan.movielens.repository.RatingRepository;
import com.yan.movielens.service.RecMovieService;
import com.yan.movielens.util.DateUtil;
import com.yan.movielens.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class RecMovieServiceImpl implements RecMovieService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private InitSystem initSystem;

    private List<RecommendedItem> recommendedItemList=null;
    private RecommenderSystem recommenderSystem;

    public void train(Integer userId,int num){

        RecommenderContext recommenderContext=new RecommenderContext(initSystem.getTrain(),
                                                                     initSystem.getSimilarity(),
                                                                     userId);

        recommenderSystem=new RecommenderSystemImpl(recommenderContext,5,num);
        recommenderSystem.similarityInit();
        recommenderSystem.recommendInit();
        recommendedItemList=recommenderSystem.getRecommendedItemList();

    }


    @Override
    public List<RecommendEntity> recMovieListInit(Integer userId, int num) {
        train(userId,num);
        return getRecMovieList(userId,num);
    }

    @Override
    public List<RecommendEntity> getRecMovieList(Integer userId, int num) {

        if(recommendedItemList==null){
            return recMovieListInit(userId,num);
        }


        //下面主要是将电影ID变成电影详细信息放入推荐列表中
        List<RecommendEntity> recList=new ArrayList<>();

        for(RecommendedItem recommendedItem:recommendedItemList){
            Integer movieId=recommendedItem.getItemId();
            Integer reason=recommendedItem.getTrueReason();

            Optional<Movie> movie=movieRepository.findById(movieId);
            if(movie.isPresent()){
                MovieDetails movieDetails=new MovieDetails(movie.get());
                movieDetails.setRating(ratingRepository.getAveScoreById(movieId));

                Optional<Movie> reasonMovie=movieRepository.findById(reason);
                if(reasonMovie.isPresent()){
                    recList.add(new RecommendEntity(movieDetails,reasonMovie.get()));
                }
            }
            System.out.print(" 电影："+movieId+" 原因："+reason+"|");
        }
        System.out.println();
        System.out.println("-------------------------------");


        return recList;

    }

    private void updateRecommendedItemList(Integer userId, Integer movieId, Double rate){
        recommenderSystem.updateRecList(userId,movieId,rate);
        recommendedItemList=recommenderSystem.getRecommendedItemList();
    }

    @Override
    public List<Movie> getSimMovieList(Integer movieId, int num) {
        Matrix sim=initSystem.getSimilarity().getSim();
        Vector simMovie=sim.row(movieId);

        Map<Integer,Double> movielist=simMovie.returnMap();
        //防止最相似的自己
        simMovie.remove(movieId);
        //对相似的电影进行排序，找出最相似的num个
        Map<Integer,Double> sortedlist= (Map<Integer, Double>) MapUtils.sortByValue(movielist,num,0);

        List<Movie> movieList=new ArrayList<>();
        for(Map.Entry<Integer,Double> entry:sortedlist.entrySet()){
            Integer simmovieId=entry.getKey();
            Optional<Movie> movie=movieRepository.findById(simmovieId);
            if(movie.isPresent()){
                movieList.add(movie.get());
            }
        }

        return movieList;
    }

    @Override
    public List<RecommendEntity> likeMovie(Integer userId, Integer movieId, Integer num) {
        if(recommendedItemList==null){
            return null;
        }
        //获得推荐原因
        int reasonId=getReason(movieId);
        //如果找不到推荐原因，说明出错了，直接返回原来的推荐列表
        if(reasonId==0){
            return getRecMovieList(userId,num);
        }

        //改变权重
        Double rate = changeWeight(userId,reasonId,1.5);

        //重新生成推荐列表
        updateRecommendedItemList(userId,reasonId,rate);
        return getRecMovieList(userId,num);
    }

    @Override
    public List<RecommendEntity> hateMovie(Integer userId, Integer movieId, Integer num) {
        if(recommendedItemList==null){
            return null;
        }
        //获得推荐原因
        int reasonId=getReason(movieId);
        //如果找不到推荐原因，说明出错了，直接返回原来的推荐列表
        if(reasonId==0){
            return getRecMovieList(userId,num);
        }

        //改变权重
        Double rate = changeWeight(userId,reasonId,0.5);

        //重新生成推荐列表
        updateRecommendedItemList(userId,reasonId,rate);
        return getRecMovieList(userId,num);
    }

    @Override
    public List<RecommendEntity> saveRate(Integer userId, Integer movieId, Double rate, Integer num) {

        //我靠，往数据库里存一个Rating这么麻烦的吗……这么多行，吓死个人啊，算了，写都写好了，懒得改了
        Rating rating=new Rating();
        UserAndMovieKey userAndMovieKey=new UserAndMovieKey();
        userAndMovieKey.setUserId(userId);
        userAndMovieKey.setMovieId(movieId);
        rating.setKey(userAndMovieKey);
        rating.setRating(rate);
        rating.setTimeStamp(Long.parseLong(DateUtil.timeStamp()));
        ratingRepository.save(rating);

        //重新生成推荐列表
        updateRecommendedItemList(userId,movieId,rate);
        return getRecMovieList(userId,num);
    }

    @Override
    public void setKAndN(int k, int n) {
        if(recommenderSystem==null){
            return;
        }
        recommenderSystem.setK(k);
        recommenderSystem.serN(n);
        recommenderSystem.recommendInit();
    }

    /**
     * 改变某位用户对某部电影的偏好权重
     * @param userId 用户ID
     * @param movieId 电影ID
     * @param weight 权重
     * @return 改变后的权重
     */
    private Double changeWeight(Integer userId, Integer movieId,Double weight){
        //将推荐原因的权重降低
        UserAndMovieKey userAndMovieKey=new UserAndMovieKey();
        userAndMovieKey.setUserId(userId);
        userAndMovieKey.setMovieId(movieId);
        Optional<Rating> rating=ratingRepository.findById(userAndMovieKey);

        //判断这位用户是否对这部电影打过分
        double rate=0.0;
        if(rating.isPresent()){
            //对原有权重进行修改
            rate=rating.get().getRating();
            rate=(rate+0.1)*weight;

            //将其存入数据库中
            Rating newRating=new Rating();
            newRating.setKey(userAndMovieKey);
            newRating.setRating(rate);
            newRating.setTimeStamp(Long.parseLong(DateUtil.timeStamp()));
            Rating rating1=ratingRepository.save(newRating);
        }

        return rate;
    }

    /**
     * 根据推荐电影ID获得这部电影的推荐原因
     * @return 推荐原因的ID
     */
    private Integer getReason(Integer movieId){
        for(RecommendedItem recommendedItem:recommendedItemList){
            int recmovieId=recommendedItem.getItemId();
            if(recmovieId==movieId){
                return recommendedItem.getTrueReason();
            }
        }
        //根本没有找到的情况下返回0，不过讲道理不可能找不到
        return 0;
    }
}
