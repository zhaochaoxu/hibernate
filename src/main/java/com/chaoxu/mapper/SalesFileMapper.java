package com.chaoxu.mapper;

import com.chaoxu.pojo.SalesFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dell on 2016/7/17.
 */
public interface SalesFileMapper {


    void save(SalesFile salesFile);

    List<SalesFile> findAll();

    List<SalesFile> findSaleFileBySaleId(Integer salesid);

    SalesFile findById(Integer id);

    void delFile(Integer salesid);
}
