package com.yan.movielens.repository;


import com.yan.movielens.entity.Collection;
import com.yan.movielens.entity.key.UserAndMovieKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, UserAndMovieKey> {

    @Query(value = "SELECT movie_id FROM `collection` WHERE user_id=?1",nativeQuery = true)
    List<Integer> getCollectionListById(Integer userId);

}
