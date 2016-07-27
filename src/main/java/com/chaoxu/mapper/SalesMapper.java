package com.chaoxu.mapper;

import com.chaoxu.pojo.Sales;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/16.
 */
public interface SalesMapper {

    void saveSales(Sales sales);

    List<Sales> findByParam(Map<String, Object> param);

    Long Count(Map<String ,Object> param);

    Long filterCount(Map<String, Object> param);

    List<Sales> findAll();

    Sales findById(Integer id);

    void updateSales(Sales sale);

    void delSaleById(Integer id);

    Long findSaleCount(@Param("start") String start, @Param("end") String end, @Param("progress") String progress);

    Float findSaleMoney(@Param("start") String start, @Param("end") String end, @Param("progress") String progress);

    List<Map<String,Object>>countProgress(@Param("start") String start, @Param("end") String end);

    List<Map<String,Object>> totalUserMoney(@Param("start") String start, @Param("end") String end);
}
