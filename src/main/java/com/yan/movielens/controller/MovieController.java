package com.yan.movielens.controller;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.PageEntity;
import com.yan.movielens.service.MovieService;
import com.yan.movielens.service.RatingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private MovieService movieService;
    private RatingService ratingService;

    public MovieController(MovieService movieService,RatingService ratingService){
        this.movieService=movieService;
        this.ratingService=ratingService;
    }

    @GetMapping(value = "/hotmovie")
    public List<MovieDetails> getHotMovie(){
        return movieService.getHotMovieDetailsList(5);
    }

    @PostMapping(value = "/movieid")
    public Movie getMovieByID(@RequestParam(value = "movieId") Integer id){
        Optional<Movie> movie=movieService.getById(id);
        if(movie.isPresent()){
            return movie.get();
        }
        return null;
    }

    @PostMapping(value = "/moviedetails")
    public MovieDetails getMovieDetailsById(@RequestParam(value = "movieId")Integer id){
        MovieDetails movieDetails=null;
        Optional<Movie> movie=movieService.getById(id);
        if(movie.isPresent()){
            movieDetails=new MovieDetails(movie.get());

            Integer movieId=movie.get().getId();
            Double movieRating=ratingService.getAveRatingById(movieId);
            movieDetails.setRating(movieRating);
        }
        return movieDetails;
    }

    @GetMapping(value = "/list")
    public PageEntity getMovieList(@RequestParam(value = "pageIndex") Integer pageIndex,
                                   @RequestParam(value = "pageSize") Integer pageSize){
        return movieService.getAllMovieList(pageIndex,pageSize);
    }

    @PostMapping(value = "/save")
    public Movie saveMovie(@RequestParam(value = "title") String title,
                           @RequestParam(value = "genres") String genres){
        Movie movie=new Movie();
        movie.setTitle(title);
        movie.setGenres(genres);
        return movieService.saveMovie(movie);
    }

    @PostMapping(value = "/delete")
    public void deleteMovie(@RequestParam(value = "movieId") Integer movieId){
        Movie movie=new Movie();
        movie.setId(movieId);
        movieService.deleteMovie(movie);
    }
}
