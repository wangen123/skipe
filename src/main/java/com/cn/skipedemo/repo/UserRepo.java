package com.cn.skipedemo.repo;

import com.cn.skipedemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,Long> {

    @Query(value = "select user from User as user where user.name = :name")
    public User findByName(@Param("name") String name);
}
