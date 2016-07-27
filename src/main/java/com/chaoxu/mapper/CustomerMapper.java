package com.chaoxu.mapper;

import com.chaoxu.pojo.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/14.
 */
public interface CustomerMapper {


    void saveCustomer(Customer customer);

    List<Customer> findByparam(Map<String, Object> param);

    List<Customer> findByType(String type);

    Long Count();

    Long FilterCount(Map<String, Object> param);

    Customer findbyId(Integer id);

    void delCustomer(Integer id);

    void updateCustomer(Customer customer);

    List<Customer> findByCompanyId(Integer id);

    List<Customer> findAll();

    Long findnewCustomerCount(@Param("start") String start, @Param("end") String end);
}
