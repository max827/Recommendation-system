package com.yan.movielens.service;

import com.yan.movielens.entity.*;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    boolean passwordMatch(Integer id,String password);

    Admin saveAdmin(Admin user);

    void deleteAdmin(Admin user);

    Optional<Admin> getById(Integer id);

    List<Admin> getAllAdminList();

    /**
     * 注销用户
     */
    void deleteUser(UserManagement userManagement, User user);

    /**
     * 插入电影
     * @return 增加或者更新的这部电影
     */
    Movie saveMovie(MovieManagement movieManagement, Movie movie);

    /**
     * 更新电影
     * @return 增加或者更新的这部电影
     */
    Movie updateMovie(MovieManagement movieManagement, Movie movie);

    /**
     * 删除电影
     */
    void deleteMovie(MovieManagement movieManagement,Movie movie);
}
