package com.yan.movielens.controller;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.Rating;
import com.yan.movielens.entity.model.HistoryEntity;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rate")
public class RatingController {

    @Autowired
    private RatingService ratingService;


    @PostMapping(value = "/update")
    public Rating updateRating(@RequestParam(value = "userId") Integer userId,
                               @RequestParam(value = "movieId") Integer movieId,
                               @RequestParam(value = "rating") Double rating,
                               @RequestParam(value = "timeStamp") long timeStamp){

        Rating rating1=new Rating();
        rating1.getKey().setUserId(userId);
        rating1.getKey().setMovieId(movieId);
        rating1.setRating(rating);
        rating1.setTimeStamp(timeStamp);

        return ratingService.setRating(rating1);
    }

    @GetMapping(value = "/highmark")
    public List<MovieDetails> getHighMarkMovieList(){
        return ratingService.getHighRateMovieList(5);
    }

    @GetMapping(value = "/history")
    public List<HistoryEntity> getHistory(@RequestParam(value = "userId") Integer userId){

        return ratingService.getHistoryByUserId(userId,10);
    }
}
