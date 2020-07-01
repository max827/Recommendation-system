package com.yan.movielens.entity.model;

import com.yan.movielens.entity.Movie;
import lombok.Data;

/**
 * 用来记录用户的历史记录
 */
@Data
public class HistoryEntity {
    private Movie movie;
    private long timeStamp;
}
