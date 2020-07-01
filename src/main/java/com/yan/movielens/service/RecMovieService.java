package com.yan.movielens.service;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.RecommendEntity;

import java.util.List;
import java.util.Map;


public interface RecMovieService {

    /**
     * 对推荐列表进行初始化
     * @param userId
     * @param num
     * @return
     */
    List<RecommendEntity> recMovieListInit(Integer userId, int num);

    /**
     * 获取推荐电影列表
     * @return 返回的电影推荐列表中电影详情信息和对应的推荐原因电影Id
     */
    List<RecommendEntity> getRecMovieList(Integer userId, int num);


    /**
     * 获取和这部电影相似的num部电影
     * @param movieId
     * @param num
     * @return
     */
    List<Movie> getSimMovieList(Integer movieId,int num);

    /**
     * 用来增加喜欢的电影的权重，并返回新的推荐列表
     * @param userId 用户ID
     * @param movieId 喜欢的电影ID
     * @param num 推荐列表大小
     * @return 新的推荐列表
     */
    List<RecommendEntity> likeMovie(Integer userId,Integer movieId, Integer num);

    /**
     * 用来减小讨厌的电影的权重，并返回新的推荐列表
     * @param userId 用户ID
     * @param movieId 讨厌的电影的ID
     * @param num 推荐列表大小
     * @return 新的推荐列表
     */
    List<RecommendEntity> hateMovie(Integer userId,Integer movieId, Integer num);

    /**
     * 重新打分就能获得新的推荐列表
     * @param userId 用户ID
     * @param movieId 电影ID
     * @param rate 评分
     * @param num 推荐列表大小
     * @return 新的推荐列表
     */
    List<RecommendEntity> saveRate(Integer userId,Integer movieId,Double rate,Integer num);

    /**
     * 重新设置K和N这两个推荐系统的参数
     * @param k 推荐系统参数
     * @param n 推荐系统参数
     */
    void setKAndN(int k,int n);
}
