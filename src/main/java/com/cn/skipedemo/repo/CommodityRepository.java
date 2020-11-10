package com.cn.skipedemo.repo;

import com.cn.skipedemo.model.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommodityRepository extends JpaRepository<Commodity,Long> {
}
