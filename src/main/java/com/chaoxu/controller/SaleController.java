package com.chaoxu.controller;

import com.chaoxu.dto.DataTablesResult;
import com.chaoxu.exception.NoFoundException;
import com.chaoxu.pojo.*;
import com.chaoxu.service.CustomerService;
import com.chaoxu.service.SalesService;
import com.chaoxu.service.UserService;
import com.chaoxu.util.ShiroUtil;
import com.chaoxu.util.Strings;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/16.
 */
@Controller
@RequestMapping("/sale")
public class SaleController {
    @Value("${filePath}")
    private String filePath;

    @Inject
    private SalesService salesService;
    @Inject
    private CustomerService customerService;
    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("sale", salesService.findAll());
        return "sale/list";
    }

    /**
     * 搜索查询所有销售机会
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Sales> loadSales(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String name = request.getParameter("name");
        name = Strings.toUTF8(name);
        String progress = request.getParameter("progress");
        progress = Strings.toUTF8(progress);
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");


        Map<String, Object> param = Maps.newHashMap();
        param.put("start", start);
        param.put("length", length);
        param.put("name", name);
        param.put("progress", progress);
        param.put("startDate", startDate);
        param.put("endDate", endDate);


        List<Sales> salesList = salesService.findByParam(param);
        Long count = salesService.Count();
        Long filterCount = salesService.filterCount(param);

        return new DataTablesResult(draw, salesList, count, filterCount);

    }


    /**
     * 通过ajax获得所有的Customer
     */
    @RequestMapping(value = "/cust.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> getAllcust() {
        return customerService.findAllCustomer();
    }


    /**
     * 添加新销售机会
     *
     * @param sales
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public String newSale(Sales sales) {
        salesService.saveSales(sales);
        return "success";
    }

    /**
     * 获取销售机会所有信息
     */
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String showSale(@PathVariable Integer id, Model model) {
        Sales sales = salesService.findById(id);
        if (sales == null) {
            throw new NoFoundException();
        }
        if (sales.getUserid() != null && !sales.getUserid().equals(ShiroUtil.getCurrentUserID()) && !ShiroUtil.isManager()) {
            throw new NoFoundException();
        }
        model.addAttribute("sale", sales);

        //获取当前销售就会的所有跟进记录
        List<SalesLog> logList = salesService.findSaleLogBysalesId(id);
        model.addAttribute("logList",logList);

        //获取当前销售机会的所有资料列表
        List<SalesFile> salesFileList = salesService.findSaleFileBySaleId(id);
        model.addAttribute("saleFileList",salesFileList);
        return "sale/view";
    }

    /**
     * 上传相关资料
     */

    @RequestMapping(value = "/file/upload",method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(MultipartFile file, Integer salesid) throws IOException {

        salesService.saveFile(file.getInputStream(),file.getOriginalFilename(),file.getSize(),file.getContentType(),salesid);
        return "success";

    }

    /**
     * 修改进度
     */
    @RequestMapping(value = "/progress/edit",method = RequestMethod.POST)
    public String editProgress(String progress,Integer id){
        salesService.updateSales(progress,id);


        return "redirect:/sale/"+id;

    }

    /**
     * 根据ID删除销售机会
     */
    @RequestMapping(value = "/del{id:\\d+}",method = RequestMethod.GET)
    public String delSale(@PathVariable Integer id){
        salesService.delSaleById(id);
        return"redirect:/sale";
    }

    /**
     * 保存跟进记录
     */
    @RequestMapping(value = "/saveSaleLog",method = RequestMethod.POST)
    public String saveSaleLog(SalesLog salesLog){

        salesService.saveSaleLog(salesLog);
        return"redirect:/sale/"+salesLog.getSalesid();

    }

}
