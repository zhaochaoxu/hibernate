package com.chaoxu.service;

import com.chaoxu.exception.NoFoundException;
import com.chaoxu.mapper.CustomerMapper;
import com.chaoxu.mapper.SalesFileMapper;
import com.chaoxu.mapper.SalesLogMapper;
import com.chaoxu.mapper.SalesMapper;

import com.chaoxu.pojo.Sales;
import com.chaoxu.pojo.SalesFile;
import com.chaoxu.pojo.SalesLog;
import com.chaoxu.util.ShiroUtil;
import com.google.common.collect.Maps;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dell on 2016/7/16.
 */
@Named
public class SalesService {

    @Inject
    private SalesMapper salesMapper;
    @Inject
    private CustomerMapper customerMapper;
    @Inject
    private SalesFileMapper salesFileMapper;
    @Inject
    private SalesLogMapper salesLogMapper;
    @Value("${filePath}")
    private String filePath;

    /**
     * 添加新销售机会
     *
     * @param sales
     */
    @Transactional
    public void saveSales(Sales sales) {


        sales.setUserid(ShiroUtil.getCurrentUserID());
        sales.setUsername(ShiroUtil.getCurrentRealName());
        sales.setCustname(customerMapper.findbyId(sales.getCustid()).getName());
        sales.setLasttime(DateTime.now().toString("yyyy-MM-dd"));

        salesMapper.saveSales(sales);

        //自动产生创建日志
        SalesLog salesLog = new SalesLog();
        salesLog.setType(SalesLog.LOG_TYPE_AUTO);
        salesLog.setContext(ShiroUtil.getCurrentRealName() + " 创建了该销售机会");
        salesLog.setSalesid(sales.getId());
        salesLogMapper.saveSaleLog(salesLog);

    }

    /**
     * 限制查询所有的 销售机会
     *
     * @param param
     * @return 返回所有的销售机会数据
     */
    public List<Sales> findByParam(Map<String, Object> param) {
        if (ShiroUtil.isEmployee()) {
            throw new NoFoundException();
        }
        return salesMapper.findByParam(param);
    }

    /**
     * 查询销售机会的总数量
     *
     * @return
     */
    public Long Count() {
        Map<String, Object> param = Maps.newHashMap();
        if (ShiroUtil.isEmployee()) {
            param.put("userid", ShiroUtil.getCurrentUserID());
        }
        return salesMapper.filterCount(param);
    }

    /**
     * 根据条件查询销售机会的数量
     *
     * @param param
     * @return
     */
    public Long filterCount(Map<String, Object> param) {
        if (ShiroUtil.isEmployee()) {
            param.put("userid", ShiroUtil.getCurrentUserID());
        }
        return salesMapper.filterCount(param);
    }

    /**
     * 查询所有的销售机会
     *
     * @return
     */
    public List<Sales> findAll() {
        return salesMapper.findAll();
    }

    /**
     * 根据ＩＤ查找销售机会
     *
     * @param id
     * @return
     */
    public Sales findById(Integer id) {
        return salesMapper.findById(id);
    }


    /**
     * 上传相关资料
     *
     * @param inputStream
     * @param originalFilename
     * @param size
     * @param contenttype
     * @param salesid
     */
    @Transactional
    public void saveFile(InputStream inputStream, String originalFilename, Long size, String contenttype, Integer salesid) {
        //判断文件是否有后缀
        String extname = "";
        if (originalFilename.lastIndexOf(".") != -1) {
            extname = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString() + extname;
        //获得输出流 将文件存到磁盘上
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(filePath, newFileName));
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NoFoundException();
        }

        SalesFile salesFile = new SalesFile();

        salesFile.setSalesid(salesid);
        salesFile.setName(originalFilename);
        salesFile.setFilename(newFileName);
        salesFile.setContenttype(contenttype);
        salesFile.setSize(size);

        salesFileMapper.save(salesFile);


    }

    /**
     * 查询所有的相关资料数据
     *
     * @return
     */
    public List<SalesFile> finaAllfile() {
        return salesFileMapper.findAll();
    }

    /**
     * 根据saleid查找相对应的资料记录
     *
     * @param salesid
     * @return
     */
    public List<SalesFile> findSaleFileBySaleId(Integer salesid) {
        return salesFileMapper.findSaleFileBySaleId(salesid);
    }

    /**
     * 根据主键获取文件
     *
     * @param id
     * @return
     */
    public SalesFile findSalesFileById(Integer id) {
        return salesFileMapper.findById(id);
    }


    /**
     * 修改销售机会进度
     *
     * @param progress
     * @param id
     */
    @Transactional
    public void updateSales(String progress, Integer id) {
        Sales sales = salesMapper.findById(id);
        if (sales == null) {
            throw new NoFoundException();
        }

        //交易完成添加交易时间
        if (progress.equals("交易完成")) {
            sales.setSuccesstime(DateTime.now().toString("yyyy-MM-dd"));
        }
        sales.setProgress(progress);
        //修改跟进时间
        sales.setLasttime(DateTime.now().toString("yyyy-MM-dd"));
        salesMapper.updateSales(sales);


        //自动添加 修改跟进记录
        SalesLog salesLog = new SalesLog();
        salesLog.setSalesid(sales.getId());
        salesLog.setType(SalesLog.LOG_TYPE_AUTO);
        salesLog.setContext(ShiroUtil.getCurrentRealName() + "更改进度为->" + progress);
        salesLogMapper.saveSaleLog(salesLog);


    }

    /**
     * 保存跟进记录
     *
     * @param salesLog
     * @param
     */
    public void saveSaleLog(SalesLog salesLog) {

        salesLog.setType(SalesLog.LOG_TYPE_INPUT);
        salesLogMapper.saveSaleLog(salesLog);

        //修改跟进时间
        Sales sales = salesMapper.findById(salesLog.getSalesid());
        sales.setLasttime(DateTime.now().toString("yyyy-MM-dd"));
        salesMapper.updateSales(sales);
    }

    /**
     * 根据Id查询当前机会的所有跟进记录
     *
     * @param salesid
     */
    public List<SalesLog> findSaleLogBysalesId(Integer salesid) {

        return salesLogMapper.findSaleLogBysalesId(salesid);

    }

    /**
     * 根据Id删除销售机会记录
     *
     * @param id
     */
    @Transactional
    public void delSaleById(Integer id) {

        Sales sales = salesMapper.findById(id);
        if(sales==null){
            throw new NoFoundException();
        }else{

            //删除相对应的文件
            List<SalesFile> salesFileList = findSaleFileBySaleId(id);
            if(salesFileList!=null){
                salesFileMapper.delFile(id);
            }
            //删除相对应的跟进记录

            List<SalesLog> logList = salesLogMapper.findSaleLogBysalesId(id);
            if(logList!=null){
                salesLogMapper.delSalesLogBysaleId(id);
            }
        }


        salesMapper.delSaleById(id);
    }

}
