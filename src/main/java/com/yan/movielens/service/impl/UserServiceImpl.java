package com.yan.movielens.service.impl;

import com.yan.movielens.entity.Movie;
import com.yan.movielens.entity.User;
import com.yan.movielens.entity.model.PageEntity;
import com.yan.movielens.repository.MovieRepository;
import com.yan.movielens.repository.UserRepository;
import com.yan.movielens.service.UserService;
import com.yan.movielens.util.PageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public boolean passwordMatch(Integer id,String password){
        Optional<User> userOptional=getById(id);
        if(userOptional.isPresent()){
            String truePassword=userOptional.get().getPassword();
            return truePassword.equals(password);
        }
        return false;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public PageEntity getAllUserList(Integer pageIndex, Integer pageSize) {

        PageHelper pageHelper=new PageHelper(userRepository.findAll(),pageSize);
        PageEntity pageEntity=new PageEntity();
        pageEntity.setList(pageHelper.getPage(pageIndex));
        pageEntity.setPageNum(pageHelper.getPageNum());
        pageEntity.setTotal(pageHelper.getDataSize());
        return pageEntity;
    }

}
