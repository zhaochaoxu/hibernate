package com.chaoxu.controller;

import com.chaoxu.dto.DataTablesResult;
import com.chaoxu.exception.NoFoundException;
import com.chaoxu.pojo.Customer;
import com.chaoxu.pojo.User;
import com.chaoxu.service.CustomerService;
import com.chaoxu.service.UserService;
import com.chaoxu.util.ShiroUtil;
import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/14.
 */

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Inject
    private CustomerService customerService;
    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("companyList",customerService.findAllcompany());
        return "customer/list";
    }

    /**
     * 列出所有客户
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Customer> loadCus(HttpServletRequest request) {
        String draw  = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");

        Map<String,Object> param =Maps.newHashMap();
        param.put("start",start);
        param.put("length",length);
        param.put("keyword",keyword);

        List<Customer> customerList = customerService.findByparam(param);
        Long count = customerService.Count();
        Long filterCount = customerService.FilterCount(param);
        return new DataTablesResult<>(draw,customerList,count,filterCount);
    }
    /**
     * 添加新客户
     *
     * @param customer
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public String Save(Customer customer) {
        customerService.saveCustomer(customer);
        return "success";
    }

    /**
     * 显示所有公司信息
     * @return
     */
    @RequestMapping(value = "/company.json",method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> showAllcompany(){
        return customerService.findAllcompany();
    }

    /**
     *根据ID删除客户
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}",method = RequestMethod.GET)
    @ResponseBody
    public String delCustomer(@PathVariable Integer id){
        customerService.delCustomer(id);
        return "success";
    }



    @RequestMapping(value = "/edit/{id:\\d+}.json",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> editCustomer(@PathVariable Integer id){

        Customer customer = customerService.findById(id);

        Map<String,Object> result = Maps.newHashMap();
        if(customer==null){
            result.put("state","error");
            result.put("message","没有找到相对应ID的客户");
        }else{
            List<Customer> companyList = customerService.findAllcompany();
            result.put("state","success");
            result.put("customer",customer);
            result.put("companyList",companyList);
        }
        return result;

    }

    /**
     * 修改客户资料
     * @param customer
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public String edit(Customer customer){
        customerService.updateCustomer(customer);
        return"success";
    }

    /**
     * 展示 客户资料
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}",method =RequestMethod.GET)
    public String viewCustomer(Model model,@PathVariable Integer id){

        Customer customer = customerService.findById(id);
        if(customer==null){
            throw new NoFoundException();
        }
        if(customer.getUserid()!=null && !customer.getUserid().equals(ShiroUtil.getCurrentUserID()) && !ShiroUtil.isManager()){

            throw new NoFoundException();
        }
        model.addAttribute("customer",customer);

        if(customer.getType().equals(Customer.TYPE_company)){
            model.addAttribute("customerList",customerService.findCustomerBycompanyid(id));

        }

        //加载所有员工
        List<User> userList = userService.findAll();
        model.addAttribute("userList",userList);

        return "customer/view";
    }

    /**
     * 公开客户
     * @param id
     * @return
     */
    @RequestMapping(value = "/open/{id:\\d+}",method = RequestMethod.GET)
    public String OpenCustomer(@PathVariable Integer id){
        Customer customer = customerService.findById(id);

        if(customer==null){
            throw  new NoFoundException();
        }

        if(customer.getUserid() != null&& !customer.getUserid().equals(ShiroUtil.getCurrentUserID())&& !ShiroUtil.isManager()){
            throw new NoFoundException();
        }
        customerService.OpenCustomer(customer);
        return"redirect:/customer";
    }

    /**
     * 转移客户
     */
    @RequestMapping(value = "/move",method = RequestMethod.POST)
    public String MoveCustomer(Integer userid,Integer id){

        Customer customer = customerService.findById(id);
        if(customer == null){
            throw new NoFoundException();
        }
        if(customer.getUserid() != null && !customer.getUserid().equals(ShiroUtil.getCurrentUserID())&!ShiroUtil.isManager()){
            throw new NoFoundException();
        }

        customerService.MoveCustomer(customer,userid);
        return "redirect:/customer";

    }

    /**
     * 制作电子名片
     */
    @RequestMapping(value = "/qrcode/{id:\\d+}.png",method = RequestMethod.GET)
    public void QrcodeCustomer(@PathVariable Integer id, HttpServletResponse response) throws WriterException, IOException {

        String meCard = customerService.makeCard(id);

        Map<EncodeHintType,String> map = Maps.newHashMap();
        map.put(EncodeHintType.CHARACTER_SET,"UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(meCard, BarcodeFormat.QR_CODE,150,150,map);
        OutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);
        outputStream.flush();
        outputStream.close();
    };

}
