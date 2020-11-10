package com.cn.skipedemo.repo;

import com.cn.skipedemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
