package com.cn.bussinessserverr.repo;

import com.cn.bussinessserverr.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
