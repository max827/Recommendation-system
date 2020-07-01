package com.yan.movielens.service.impl;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.Rating;
import com.yan.movielens.entity.User;
import com.yan.movielens.entity.key.UserAndMovieKey;
import com.yan.movielens.entity.model.HistoryEntity;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.repository.MovieRepository;
import com.yan.movielens.repository.RatingRepository;
import com.yan.movielens.repository.UserRepository;
import com.yan.movielens.service.RatingService;
import com.yan.movielens.util.CommonUtils;
import com.yan.movielens.util.MapUtils;
import com.yan.movielens.util.PageHelper;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private MovieRepository movieRepository;
    private UserRepository userRepository;

    public RatingServiceImpl(MovieRepository movieRepository,
                             RatingRepository ratingRepository,
                             UserRepository userRepository){
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository=userRepository;
    }

    @Override
    public List<Integer> getAllRatedMovies() {
        return ratingRepository.getAllRatedMovies();
    }

    @Override
    public Optional<Rating> getRating(Integer userId, Integer movieId) {
        UserAndMovieKey ratingKey=new UserAndMovieKey();
        ratingKey.setMovieId(movieId);
        ratingKey.setUserId(userId);

        return ratingRepository.findById(ratingKey);
    }

    @Override
    public Double getAveRatingById(Integer id) {

        return ratingRepository.getAveScoreById(id);
    }

    @Override
    public List<MovieDetails> getHighRateMovieList(Integer num) {

        //用来记录每个电影的评分情况
        Map<Integer,Double> movieScores=new HashMap<>();

        List movieScore=ratingRepository.getAveScoreList();
        for(Object obj:movieScore){
            Object[] objects=(Object[])obj;
            movieScores.put(Integer.parseInt(objects[0].toString()),Double.parseDouble(objects[1].toString()));
        }

        //排序
        Map<Integer,Double> movieIdMap= (Map<Integer, Double>) MapUtils.sortByValue(movieScores,num,0);
        List<MovieDetails> movieDetailsList=new ArrayList<>();

        for(Map.Entry<Integer,Double> entry:movieIdMap.entrySet()){
            Integer movieId=entry.getKey();
            Double aveRate=entry.getValue();
            Optional<Movie> movieOptional=movieRepository.findById(movieId);
            if(movieOptional.isPresent()){
                MovieDetails movieDetails=new MovieDetails(movieOptional.get());
                movieDetails.setRating(aveRate);
                movieDetailsList.add(movieDetails);
            }
        }

        return movieDetailsList;
    }




    @Override
    public List<HistoryEntity> getHistoryByUserId(Integer id, int num) {
        List rated=ratingRepository.getAllMovieIdByUserId(id);

        //对num的大小进行限制，没必要让用户知道那么多历史记录
        if(num>20){
            num=20;
        }
        if(num>=rated.size()){
            num=rated.size();
        }

        Collections.sort(rated, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                Object[] objects1=(Object[])o1;
                long tempstamp1=Long.parseLong(objects1[1].toString());

                Object[] objects2=(Object[])o2;
                long tempstamp2=Long.parseLong(objects2[1].toString());

                //额，这是我从Double这个数据类型的源码复制的，刚好那里也有long这个数据类型的比较
                return (tempstamp1 == tempstamp2 ?  0 : // Values are equal
                        (tempstamp1 < tempstamp2 ? 1 : // (-0.0, 0.0) or (!NaN, NaN)
                         -1));
            }
        });

        //为了尽可能得少访问数据库，所以先在前面排好序，再在下面访问前几个的历史记录
        List<HistoryEntity> history=new ArrayList<>();
        for(int i=0;i<num;i++){
            //处理得到的数据将其放入HistoryEntity实体中
            Object[] objects=(Object[])rated.get(i);
            HistoryEntity historyEntity=new HistoryEntity();
            Integer movieId=Integer.parseInt(objects[0].toString());
            Optional<Movie> movie=movieRepository.findById(movieId);
            if(movie.isPresent()){
                historyEntity.setMovie(movie.get());
            }
            historyEntity.setTimeStamp(Long.parseLong(objects[1].toString()));

            history.add(historyEntity);
        }

        return history;
    }

    @Override
    public List<User> getUserListByMovieId(Integer id) {
        List<Integer> userIds=ratingRepository.getAllUserIdByMovieId(id);
        List<User> userList=new ArrayList<User>();
        for(Integer userId:userIds){
            userList.add(userRepository.getOne(userId));
        }
        return userList;
    }

    @Override
    public Rating setRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getRandomList(int num) {
        return ratingRepository.getRandomList(num);
    }


}
