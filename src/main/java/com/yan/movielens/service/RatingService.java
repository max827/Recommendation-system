package com.yan.movielens.service;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.Rating;
import com.yan.movielens.entity.User;
import com.yan.movielens.entity.model.HistoryEntity;
import com.yan.movielens.entity.model.MovieDetails;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    /**
     * 获取所有被评价过的电影列表
     * @return 电影Id列表
     */
    List<Integer> getAllRatedMovies();

    /**
     * 根据用户Id和电影Id查找评价
     * @param userId 用户Id
     * @param movieId 电影Id
     * @return
     */
    Optional<Rating> getRating(Integer userId, Integer movieId);

    /**
     * 根据id获得电影的平均评分
     * @param id
     * @return
     */
    Double getAveRatingById(Integer id);

    /**
     * 查询评分高电影列表
     * @param num 需要排名前几的高评分电影
     * @return 电影列表
     */
    List<MovieDetails> getHighRateMovieList(Integer num);

    /**
     * 查找这个用户评分过的电影列表,即历史记录
     * @param id 用户id
     * @param num 取最近的num个评分
     * @return 电影列表
     */
    List<HistoryEntity> getHistoryByUserId(Integer id, int num);

    /**
     * 查找为这部电影评分过的用户列表
     * @param id 电影Id
     * @return 用户列表
     */
    List<User> getUserListByMovieId(Integer id);

    /**
     * 保存评分
     * @param rating
     * @return
     */
    Rating setRating(Rating rating);


    /**
     * 随机获取num个评价
     * @param num 随机个数
     * @return num个评价
     */
    List<Rating> getRandomList(int num);

}
