package com.yan.movielens.controller;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.RecommendEntity;
import com.yan.movielens.service.RecMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rec")
public class RecMovieController {

    @Autowired
    private RecMovieService recMovieService;

    @PostMapping(value = "/reclist")
    public List<RecommendEntity> getRecList(@RequestParam(value = "userId") Integer userId){
        return recMovieService.recMovieListInit(userId,5);
    }

    @PostMapping(value = "/like")
    public List<RecommendEntity> beInterestedIn(@RequestParam(value = "userId") Integer userId,
                                                @RequestParam(value = "movieId") Integer movieId){

        return recMovieService.likeMovie(userId,movieId,10);
    }

    @PostMapping(value = "/hate")
    public List<RecommendEntity> notInterestedIn(@RequestParam(value = "userId") Integer userId,
                                                 @RequestParam(value = "movieId") Integer movieId){
        return recMovieService.hateMovie(userId,movieId,10);
    }

    @PostMapping(value = "/similarity")
    public List<Movie> getSimMovieList(@RequestParam(value = "movieId") Integer movieId){
        return recMovieService.getSimMovieList(movieId,5);
    }

    @PostMapping(value = "/newrate")
    public List<RecommendEntity> saveNewRate(@RequestParam(value = "userId") Integer userId,
                                             @RequestParam(value = "movieId") Integer movieId,
                                             @RequestParam(value = "rate") Double rate){

        return recMovieService.saveRate(userId,movieId,rate,10);
    }


}
