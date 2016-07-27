package com.chaoxu.mapper;

import com.chaoxu.pojo.Document;

import java.util.List;

/**
 * Created by dell on 2016/7/13.
 */
public interface DocumentMapper {
    /**
     *根据fid（父id）查找文件
     * @param fid
     * @return
     */
    List<Document> findDocByFid(Integer fid);

    /**
     * 保存文件
     * @param document
     */
    void saveDir(Document document);

    /**
     * 根据ID查询文件
     * @param id
     * @return
     */
    Document findDocById(Integer id);
}
