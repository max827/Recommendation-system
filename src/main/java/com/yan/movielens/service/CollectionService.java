package com.yan.movielens.service;

import com.yan.movielens.entity.Collection;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.PageEntity;


import java.util.List;

public interface CollectionService {

    /**
     * 保存收藏结果
     * @return 收藏返回结果
     */
    Collection saveCollection(Collection collection);

    /**
     * 移除收藏
     */
    void deleteCollection(Integer userId, Integer movieId);

    /**
     * 根据用户id查询收藏列表
     * @param userId 用户id
     * @return 收藏列表的电影Id
     */
    PageEntity getColListById(Integer userId, Integer pageIndex, Integer pageSize);
}
