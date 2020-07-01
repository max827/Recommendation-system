package com.yan.movielens.repository;


import com.yan.movielens.entity.Collection;
import com.yan.movielens.service.CollectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class CollectionRepositoryTest {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CollectionService collectionService;

    @Test
    void test(){

        collectionService.deleteCollection(5,10);

    }

}