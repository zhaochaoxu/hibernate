package com.chaoxu.controller;

import com.chaoxu.service.ChartService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/19.
 */
@Controller
@RequestMapping("/chart")
public class ChartController {

    @Inject
    private ChartService chartService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model,
                       @RequestParam(required = false, defaultValue = "") String start,
                       @RequestParam(required = false, defaultValue = "") String end) {

        long newCustomerCount = chartService.findNewCustomerCount(start, end);
        long saleCount = chartService.findSaleCount(start, end);
        Float saleMoney = chartService.findSaleMoney(start, end);

        model.addAttribute("newCustomerCount", newCustomerCount);
        model.addAttribute("saleCount", saleCount);
        model.addAttribute("saleMoney", saleMoney);
        return "chart/list";
    }

    //饼状图
    @RequestMapping(value = "/progress/load", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> loadPieData(@RequestParam(required = false, defaultValue = "") String start,
                                                 @RequestParam(required = false, defaultValue = "") String end) {

        return chartService.loadPieData(start, end);

    }

    //柱状图
    @RequestMapping(value = "/user/money", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadBarData(@RequestParam(required = false, defaultValue = "") String start,
                                           @RequestParam(required = false, defaultValue = "") String end) {
        List<Map<String, Object>> data = chartService.loadBarData(start, end);

        List<String> names = Lists.newArrayList();
        List<Object> values = Lists.newArrayList();

        for (Map<String, Object> maps : data) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                if (entry.getKey().equals("realname")) {
                    names.add(entry.getValue().toString());
                }
                if(entry.getKey().equals("price")){
                    values.add(entry.getValue().toString());
                }
            }
        }

        Map<String,Object> result = Maps.newHashMap();
        result.put("names",names);
        result.put("values",values);

        return result;
    }
}
