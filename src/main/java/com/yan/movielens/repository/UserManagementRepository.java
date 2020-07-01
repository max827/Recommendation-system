package com.yan.movielens.repository;

import com.yan.movielens.entity.UserManagement;
import com.yan.movielens.entity.key.AdminKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<UserManagement, AdminKey> {
}
