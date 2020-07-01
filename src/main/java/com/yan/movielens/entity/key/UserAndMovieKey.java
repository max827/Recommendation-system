package com.yan.movielens.entity.key;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class UserAndMovieKey implements Serializable {

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "movieId")
    private Integer movieId;
    // 省略setter,getter方法

    @Override
    public String toString() {
        return "PeopleKey [userId=" + userId + ", movieId=" + movieId + "]";
    }

}
