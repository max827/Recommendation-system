package com.yan.movielens.service.impl;


import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.Rating;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.PageEntity;
import com.yan.movielens.repository.MovieRepository;
import com.yan.movielens.repository.RatingRepository;
import com.yan.movielens.service.MovieService;
import com.yan.movielens.service.RatingService;
import com.yan.movielens.util.MapUtils;
import com.yan.movielens.util.PageHelper;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;
    private RatingService ratingService;

    public MovieServiceImpl(MovieRepository movieRepository,
                            RatingRepository ratingRepository,
                            RatingService ratingService){
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.ratingService=ratingService;
    }


    @Override
    public Movie saveMovie(Movie movie) {
        return  movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }


    @Override
    public PageEntity getAllMovieList(Integer pageIndex,Integer pageSize) {
        PageHelper pageHelper=new PageHelper(movieRepository.findAll(),pageSize);
        PageEntity pageEntity=new PageEntity();
        pageEntity.setList(pageHelper.getPage(pageIndex));
        pageEntity.setPageNum(pageHelper.getPageNum());
        pageEntity.setTotal(pageHelper.getDataSize());
        return pageEntity;
    }

    @Override
    public List<MovieDetails> getHotMovieDetailsList(Integer num) {

        List<MovieDetails> movieDetailsList= new ArrayList<>();
        List<Movie> movieList=getHotMovieList(num);
        for(Movie movie:movieList){
            MovieDetails movieDetails=new MovieDetails(movie);
            Integer movieId=movie.getId();
            movieDetails.setRating(ratingService.getAveRatingById(movieId));
            movieDetailsList.add(movieDetails);
        }
        return movieDetailsList;
    }

    @Override
    public Optional<Movie> getById(Integer id) {
        return movieRepository.findById(id);
    }


    private List<Movie> getHotMovieList(Integer num) {

        Map<Integer,Integer> movieHits=new HashMap<>();

        List movieHit=ratingRepository.getMovieHitsList();
        for(Object obj:movieHit){
            Object[] objects=(Object[])obj;
            movieHits.put(Integer.parseInt(objects[0].toString()),Integer.parseInt(objects[1].toString()));
        }

        List<Integer> movieIdList=MapUtils.sortByValueList(movieHits,num,0);
        List<Movie> movieDetailsList=new ArrayList<>();

        for(Integer movieId:movieIdList){
            Optional<Movie> movieOptional=getById(movieId);
            if(movieOptional.isPresent()){
                movieDetailsList.add(movieOptional.get());
            }
        }

        return movieDetailsList;
    }


    @Override
    public Optional<MovieDetails> getDetailsById(Integer id) {
        Optional<Movie> movie= getById(id);

        MovieDetails movieDetails=null;

        if(movie.isPresent()){
            movieDetails=new MovieDetails(movie.get());
            return Optional.of(movieDetails);
        }

        return Optional.ofNullable(movieDetails);
    }

}
