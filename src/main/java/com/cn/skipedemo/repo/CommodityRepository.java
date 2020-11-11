package com.cn.skipedemo.repo;

import com.cn.skipedemo.model.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommodityRepository extends JpaRepository<Commodity,Long> {


    @Query(value = "select commodity from Commodity as commodity where commodity.commodityName = :name")
    Commodity findByName(@Param("name") String name);
}
