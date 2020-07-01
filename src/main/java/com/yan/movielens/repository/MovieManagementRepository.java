package com.yan.movielens.repository;


import com.yan.movielens.entity.MovieManagement;
import com.yan.movielens.entity.key.AdminKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieManagementRepository extends JpaRepository<MovieManagement, AdminKey> {
}
