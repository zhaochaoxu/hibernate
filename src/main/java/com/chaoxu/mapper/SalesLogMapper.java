package com.chaoxu.mapper;

import com.chaoxu.pojo.SalesLog;

import java.util.List;

/**
 * Created by dell on 2016/7/17.
 */
public interface SalesLogMapper {

    void saveSaleLog(SalesLog salesLog);

    List<SalesLog> findSaleLogBysalesId(Integer salesid);

    void delSalesLogBysaleId(Integer salesid);
}
