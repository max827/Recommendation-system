package com.yan.movielens.entity;


import com.yan.movielens.entity.key.UserAndMovieKey;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Data
@Entity
public class Collection {

    /**
     * 复合主键
     */
    @EmbeddedId
    private UserAndMovieKey key;

    @Column(name="timestamp",columnDefinition = "bigint")
    private long timeStamp;

    public Collection(){
        this.key=new UserAndMovieKey();
    }
    public Collection(int userId,int movieId,long timeStamp){
        this.key=new UserAndMovieKey();
        this.key.setUserId(userId);
        this.key.setMovieId(movieId);
        this.timeStamp=timeStamp;
    }
}
