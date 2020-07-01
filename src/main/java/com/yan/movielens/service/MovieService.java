package com.yan.movielens.service;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.PageEntity;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    /**
     * 增加或者更新电影
     * @return 增加或者更新的这部电影
     */
    Movie saveMovie(Movie movie);

    /**
     * 删除电影
     */
    void deleteMovie(Movie movie);



    /**
     * 查询所有电影列表
     * @return 电影列表
     */
    PageEntity getAllMovieList(Integer pageIndex, Integer pageSize);


    /**
     * 查询热门电影列表
     * @param num 需要前几的热门电影
     * @return 电影列表
     */
    List<MovieDetails> getHotMovieDetailsList(Integer num);

    /**
     * 根据id查询电影基本信息
     * @param id 电影id
     * @return id对应的电影
     */
    Optional<Movie> getById(Integer id);

    /**
     * 根据id查询电影详情信息
     * @param id 电影id
     * @return id对应的电影
     */
    Optional<MovieDetails> getDetailsById(Integer id);


}
