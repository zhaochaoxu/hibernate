package com.chaoxu.service;

import com.chaoxu.mapper.CustomerMapper;
import com.chaoxu.mapper.SalesMapper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/19.
 */

@Named
public class ChartService {

    @Inject
    private CustomerMapper customerMapper;
    @Inject
    private SalesMapper salesMapper;

    String progress = "交易完成";
    DateTime now = new DateTime();
    /**
     * 统计新增区间的客户数量
     *
     * @param start
     * @param end
     * @return
     */
    public Long findNewCustomerCount(String start, String end) {

        if(StringUtils.isEmpty(start)){
            start=now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)){
            end = now.toString("yyyy-MM-dd");
        }
        return customerMapper.findnewCustomerCount(start,end);
    }

    /**
     * 查询期间的销售机会完成交易的数量
     * @param start
     * @param end
     * @return
     */
    public Long findSaleCount(String start, String end) {

        if(StringUtils.isEmpty(start)){
            start=now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)){
            end = now.toString("yyyy-MM-dd");
        }

        return salesMapper.findSaleCount(start,end,progress);
    }

    /**
     * 查询交易完成的总效益价值
     * @param start
     * @param end
     * @return
     */
    public Float findSaleMoney(String start, String end) {
        String progress = "交易完成";

        if(StringUtils.isEmpty(start)){
            start= now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)){
            end = now.toString("yyyy-MM-dd");
        }
        return salesMapper.findSaleMoney(start,end,progress);
    }

    /**
     * 查询期间的销售机会数量  饼状图
     * @param start
     * @param end
     * @return
     */
    public List<Map<String, Object>> loadPieData(String start, String end) {

        if(StringUtils.isEmpty(start)){
            start= now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)){
            end = now.toString("yyyy-MM-dd");
        }

        return salesMapper.countProgress(start,end);
    }

    public List<Map<String, Object>> loadBarData(String start, String end) {
        if(StringUtils.isEmpty(start)){
            start= now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)){
            end = now.toString("yyyy-MM-dd");
        }

        return salesMapper.totalUserMoney(start,end) ;
    }

}
