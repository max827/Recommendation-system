package com.yan.movielens.repository;

import com.yan.movielens.annotation.MyFirstAnnotation;
import com.yan.movielens.annotation.Time;
import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.model.HistoryEntity;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.service.RatingService;
import com.yan.movielens.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    @Time
    @Test
    @MyFirstAnnotation(value = "吃饭")
    void test1(){
        System.out.println("运行");
//        List<HistoryEntity> a=ratingService.getMovieListByUserId(20,5);
//        for(HistoryEntity historyEntity:a){
//            Long timestamp=historyEntity.getTimeStamp();
//            System.out.println(DateUtil.timeStamp2Date(timestamp.toString(), "yyyy-MM-dd HH:mm:ss"));
//        }

    }

    @Test
    void test2(){
        List<MovieDetails> movieDetailsList=ratingService.getHighRateMovieList(5);
        for(MovieDetails movieDetails:movieDetailsList){
            System.out.println(movieDetails.getTitle());
        }
    }
}