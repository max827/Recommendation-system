package com.yan.movielens.entity;



import com.yan.movielens.entity.key.UserAndMovieKey;
import lombok.Data;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class Rating implements Serializable {

    /**
     * 复合主键
     */
    @EmbeddedId
    private UserAndMovieKey key;

    @Column(name="rating",columnDefinition = "double(16,2) not null")
    private Double rating;

    @Column(name="timestamp",columnDefinition = "bigint")
    private long timeStamp;

    public Rating(){

    }
    public Rating(Integer userId,Integer movieId,Double rating,Long timeStamp){
        this.key=new UserAndMovieKey();
        this.key.setUserId(userId);
        this.key.setMovieId(movieId);
        this.rating=rating;
        this.timeStamp=timeStamp;
    }

}
