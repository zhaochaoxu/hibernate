package com.chaoxu.service;

import com.chaoxu.mapper.CustomerMapper;
import com.chaoxu.pojo.Customer;
import com.chaoxu.util.ShiroUtil;
import com.chaoxu.util.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/14.
 */
@Named
public class CustomerService {

    @Inject
    private CustomerMapper customerMapper;


    /**
     * 保存客户
     *
     * @param customer
     */
    public void saveCustomer(Customer customer) {
        if (customer.getCompanyid() != null) {
            Customer company = customerMapper.findbyId(customer.getCompanyid());
            customer.setCompanyname(company.getName());

        }
        customer.setUserid(ShiroUtil.getCurrentUserID());
        customer.setPingyin(Strings.toPinyin(customer.getName()));
        customerMapper.saveCustomer(customer);
    }

    /**
     * 根据Id查询客户
     *
     * @param id
     * @return 返回一个客户
     */
    public Customer findById(Integer id) {
        return customerMapper.findbyId(id);
    }

    /**
     * 搜索查询所有客户
     *
     * @param param
     * @return 返回搜索出来的所有客户
     */
    public List<Customer> findByparam(Map<String, Object> param) {
        if(ShiroUtil.isEmployee()){
            param.put("userid",ShiroUtil.getCurrentUserID());
        }

        return customerMapper.findByparam(param);
    }

    /**
     * 查询客户总数量
     *
     * @return 返回客户总数量
     */
    public Long Count() {
        if(ShiroUtil.isEmployee()){
            Map<String,Object> param = Maps.newHashMap();
            param.put("userid",ShiroUtil.getCurrentUserID());
            return customerMapper.FilterCount(param);
        }
        return customerMapper.Count();
    }

    /**
     * 限制查询客户数据
     *
     * @param param 限制的条件
     * @return 返回限制查询的数量
     */
    public Long FilterCount(Map<String, Object> param) {
        if(ShiroUtil.isEmployee()){
            param.put("userid",ShiroUtil.getCurrentUserID());
        }
        return customerMapper.FilterCount(param);
    }

    /**
     * 根据类型查询所有的公司
     * @return  返回查询到的所有公司
     */
    public List<Customer> findAllcompany() {
        return customerMapper.findByType(Customer.TYPE_company);
    }

    /**
     * 根据ID删除客户
     *
     * @param id
     */
    public void delCustomer(Integer id) {
        customerMapper.delCustomer(id);
    }

    /**
     * 修改客户资料
     *
     * @param customer
     */
    @Transactional
    public void updateCustomer(Customer customer) {
        if (customer.getType().equals(Customer.TYPE_company)) {

            //查找相关联的客户
            List<Customer> customerList = customerMapper.findByCompanyId(customer.getId());
            for (Customer cus:customerList) {
                cus.setCompanyid(customer.getId());
                cus.setCompanyname(customer.getName());
                customerMapper.updateCustomer(cus);

            }

        }else{
            if(customer.getCompanyid()!= null){
                Customer company = customerMapper.findbyId(customer.getCompanyid());
                customer.setCompanyname(company.getName());
            }
        }
        customer.setPingyin(Strings.toPinyin(customer.getName()));
        customerMapper.updateCustomer(customer);
    }

    /**
     * 根据Companyid 查询一个公司
     * @param id
     * @return 返回一个公司
     */
    public List<Customer> findCustomerBycompanyid(Integer id) {
        return customerMapper.findByCompanyId(id);
    }

    /**
     * 公开客户
     * @param customer
     */
    public void OpenCustomer(Customer customer) {
        customer.setUserid(null);
        customerMapper.updateCustomer(customer);
    }

    /**
     * 转移客户
     * @param customer
     * @param userid
     */
    public void MoveCustomer(Customer customer,Integer userid) {
        customer.setUserid(userid);
        customerMapper.updateCustomer(customer);
    }

    /**
     * 根据ID找到客户信息然后生成二维码
     * @param id
     * @return 返回二维码的字符串
     */

    public String makeCard(Integer id){
        Customer customer = customerMapper.findbyId(id);

        StringBuilder macard = new StringBuilder("MECARD");
        if(StringUtils.isNotEmpty(customer.getName())){
            macard.append("N:"+customer.getName()+";");
        }
        if(StringUtils.isNotEmpty(customer.getTel())){
            macard.append("TEL:"+customer.getTel()+";");
        }
        if(StringUtils.isNotEmpty(customer.getCompanyname())){
            macard.append("ORG:"+customer.getCompanyname()+";");
        }
         if(StringUtils.isNotEmpty(customer.getEmail())){
             macard.append("EMAIL:"+customer.getEmail()+";");
         }
        if(StringUtils.isNotEmpty(customer.getAddress())){
            macard.append("ADR:"+customer.getAddress()+";");
        }
        macard.append(";");

        return macard.toString();
    }

    /**
     *查询所有的客户
     */
    public List<Customer> findAllCustomer() {
        return customerMapper.findAll();
    }
}
