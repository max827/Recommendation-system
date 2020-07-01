package com.yan.movielens.service.impl;

import com.yan.movielens.entity.*;
import com.yan.movielens.repository.AdminRepository;
import com.yan.movielens.repository.MovieManagementRepository;
import com.yan.movielens.repository.UserManagementRepository;
import com.yan.movielens.service.AdminService;
import com.yan.movielens.service.MovieService;
import com.yan.movielens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserManagementRepository userManagementRepository;
    @Autowired
    private MovieManagementRepository movieManagementRepository;

    @Override
    public boolean passwordMatch(Integer id, String password) {
        Optional<Admin> adminOptional=getById(id);
        if(adminOptional.isPresent()){
            String truePassword=adminOptional.get().getPassword();
            return truePassword.equals(password);
        }
        return false;
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

    @Override
    public Optional<Admin> getById(Integer id) {
        return adminRepository.findById(id);
    }

    @Override
    public List<Admin> getAllAdminList() {
        return adminRepository.findAll();
    }

    //以下是对用户和电影的管理

    @Override
    public void deleteUser(UserManagement userManagement, User user) {
        userManagementRepository.save(userManagement);
        userService.deleteUser(user);
    }

    @Override
    public Movie saveMovie(MovieManagement movieManagement, Movie movie) {
        Movie newMovie=movieService.saveMovie(movie);
        movieManagement.getKey().setThatId(newMovie.getId());
        movieManagementRepository.save(movieManagement);
        return newMovie;
    }

    @Override
    public Movie updateMovie(MovieManagement movieManagement, Movie movie) {
        movieManagementRepository.save(movieManagement);
        return movieService.saveMovie(movie);
    }

    @Override
    public void deleteMovie(MovieManagement movieManagement, Movie movie) {
        movieManagementRepository.save(movieManagement);
        movieService.deleteMovie(movie);
    }
}
