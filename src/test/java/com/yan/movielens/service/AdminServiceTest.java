package com.yan.movielens.service;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.MovieManagement;
import com.yan.movielens.repository.CollectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Test
    void test(){
        MovieManagement movieManagement=new MovieManagement(1,1,2,3);
        Movie movie=new Movie();
        movie.setId(1);
        movie.setGenres("Comedy|Children");
        movie.setTitle("ToyStory");
        adminService.updateMovie(movieManagement,movie);

    }
}