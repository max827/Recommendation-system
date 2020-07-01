package com.yan.movielens.service;


import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.User;
import com.yan.movielens.entity.model.PageEntity;

import java.util.List;
import java.util.Optional;


public interface UserService{
    boolean passwordMatch(Integer id,String password);

    User saveUser(User user);

    void deleteUser(User user);

    Optional<User> getById(Integer id);

    PageEntity getAllUserList(Integer pageIndex, Integer pageSize);

}
