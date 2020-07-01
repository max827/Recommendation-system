package com.yan.movielens.controller;

import com.yan.movielens.entity.User;
import com.yan.movielens.entity.model.PageEntity;
import com.yan.movielens.service.UserService;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping(value = "/login")
    public User login(@RequestParam(value = "userId") Integer userId,
                      @RequestParam(value = "password") String password){

        if(userService.passwordMatch(userId,password)){
            Optional<User> userOptional=userService.getById(userId);
            return userOptional.get();
        }else{
           return null;
        }

    }

    @PostMapping(value = "/register")
    public User register(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password){
        User newUser=new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        return userService.saveUser(newUser);

    }

    @GetMapping(value = "/userlist")
    public PageEntity getAllUserList(@RequestParam(value = "pageIndex") Integer pageIndex,
                                     @RequestParam(value = "pageSize") Integer pageSize){
        return userService.getAllUserList(pageIndex,pageSize);
    }

    @PostMapping(value = "/userinfo")
    public User getUserById(@RequestParam(value = "userId") Integer userId){
        Optional<User> user=userService.getById(userId);
        if(user.isPresent())
            return user.get();
        return null;
    }

    @PostMapping(value = "/logout")
    public void deleteUser(@RequestParam(value = "userId") Integer userId){
        User user=new User();
        user.setId(userId);
        userService.deleteUser(user);
    }
}
