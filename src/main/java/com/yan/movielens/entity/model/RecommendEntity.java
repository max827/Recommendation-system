package com.yan.movielens.entity.model;

import com.yan.movielens.entity.Movie;
import lombok.Data;


/**
 * 用来记录推荐电影相关的信息
 */
@Data
public class RecommendEntity {
    MovieDetails movieDetails;
    Movie reason;

    public RecommendEntity(MovieDetails movieDetails,Movie reason){
        this.movieDetails=movieDetails;
        this.reason=reason;
    }
}
