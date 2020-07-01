package com.yan.movielens.entity.model;

import com.yan.movielens.entity.Movie;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class MovieDetails {
    private Integer id;
    private String title;
    private String genres;
    private Double rating;
    private String introduction;

    public MovieDetails(Movie movie){
        this.id=movie.getId();
        this.genres=movie.getGenres();
        this.title=movie.getTitle();
        this.rating=0.0;
        this.introduction="this is a good movie";
    }

    public void setRating(Double rating){
        BigDecimal b   =   new   BigDecimal(rating);
        this.rating= b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
