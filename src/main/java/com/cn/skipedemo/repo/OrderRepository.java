package com.cn.skipedemo.repo;

import com.cn.skipedemo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
