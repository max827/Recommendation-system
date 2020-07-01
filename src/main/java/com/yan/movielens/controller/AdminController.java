package com.yan.movielens.controller;


import com.yan.movielens.entity.*;
import com.yan.movielens.service.AdminService;
import com.yan.movielens.service.RecMovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private AdminService adminService;
    private RecMovieService recMovieService;

    public AdminController(AdminService adminService,RecMovieService recMovieService){
        this.recMovieService=recMovieService;
        this.adminService=adminService;
    }

    @PostMapping(value = "/login")
    public Admin login(@RequestParam(value = "adminId") Integer adminId,
                       @RequestParam(value = "password") String password){

        if(adminService.passwordMatch(adminId,password)){
            Optional<Admin> adminOptional=adminService.getById(adminId);
            return adminOptional.get();
        }else{
            return null;
        }

    }

    @PostMapping(value = "/register")
    public Admin register(@RequestParam(value = "adminname") String adminname,
                         @RequestParam(value = "password") String password){
        Admin newAdmin=new Admin();
        newAdmin.setAdminname(adminname);
        newAdmin.setPassword(password);
        return adminService.saveAdmin(newAdmin);

    }

    @GetMapping(value = "/alladminlist")
    public List<Admin> getAllAdminList(){
        return adminService.getAllAdminList();
    }

    @PostMapping(value = "/admininfo")
    public Admin getAdminById(@RequestParam(value = "adminId") Integer adminId){
        Optional<Admin> admin=adminService.getById(adminId);
        if(admin.isPresent())
            return admin.get();
        return null;
    }

    @PostMapping(value = "movie/delete")
    public void deleteMovie(@RequestParam(value = "adminId") Integer adminId,
                            @RequestParam(value = "movieId") Integer movieId,
                            @RequestParam(value = "timeStamp") long timeStamp){
        MovieManagement movieManagement=new MovieManagement(adminId,movieId,2,timeStamp);
        Movie movie=new Movie();
        movie.setId(movieId);
        adminService.deleteMovie(movieManagement,movie);
    }
    @PostMapping(value = "movie/save")
    public Movie saveMovie(@RequestParam(value = "adminId") Integer adminId,
                           @RequestParam(value = "title") String title,
                           @RequestParam(value = "genres") String genres,
                           @RequestParam(value = "timeStamp") long timeStamp){
        Movie movie=new Movie();
        movie.setTitle(title);
        movie.setGenres(genres);

        MovieManagement movieManagement=new MovieManagement(adminId,1,timeStamp);
        return adminService.saveMovie(movieManagement,movie);
    }

    @PostMapping(value = "movie/update")
    public Movie updateMovie(@RequestParam(value = "adminId") Integer adminId,
                             @RequestParam(value = "movieId") Integer movieId,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "genres") String genres,
                             @RequestParam(value = "timeStamp") long timeStamp){
        Movie movie=new Movie();
        movie.setId(movieId);
        movie.setTitle(title);
        movie.setGenres(genres);

        MovieManagement movieManagement=new MovieManagement(adminId,1,timeStamp);
        return adminService.saveMovie(movieManagement,movie);
    }

    @PostMapping(value = "user/delete")
    public void deleteUser(@RequestParam(value = "adminId") Integer adminId,
                           @RequestParam(value = "userId") Integer userId,
                           @RequestParam(value = "timeStamp") long timeStamp){
        UserManagement userManagement=new UserManagement(adminId,userId,2,timeStamp);
        User user=new User();
        user.setId(userId);
        adminService.deleteUser(userManagement,user);
    }

    @PostMapping(value = "/parameter")
    public void setParameter(@RequestParam(value = "k") Integer k,
                             @RequestParam(value = "n") Integer n){
        recMovieService.setKAndN(k,n);
    }
}
