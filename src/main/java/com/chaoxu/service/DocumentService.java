package com.chaoxu.service;

import com.chaoxu.mapper.DocumentMapper;
import com.chaoxu.pojo.Document;
import com.chaoxu.util.ShiroUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by dell on 2016/7/13.
 */
@Named
public class DocumentService {


    @Inject
    private DocumentMapper documentMapper;
    @Value("${filePath}")
    private String filePath;

    /**
     * 根据fid查找文件夹
     *
     * @param fid
     * @return
     */
    public List<Document> findDocByFid(Integer fid) {
        return documentMapper.findDocByFid(fid);
    }

    /**
     * 新建文件夹
     *
     * @param
     */
    public void saveDir(Document document) {
        //String name,Integer fid  document.setName(name);document.setFid(fid);

        document.setType(Document.TYPE_DIR);
        document.setCreateuser(ShiroUtil.getCurrentUserName());
        documentMapper.saveDir(document);
    }

    /**
     * 上传文件
     *
     * @param inputStream      文件输入流露
     * @param originalFilename 文件的原始名字
     * @param contentType      文件的类型
     * @param size             文件的大小
     * @param fid              文件所在的文件夹 的ID
     */
    @Transactional
    public void saveFile(InputStream inputStream, String originalFilename, String contentType, long size, Integer fid) {

        //判断文件是否有后缀
        String extname = "";
        if (originalFilename.lastIndexOf(".") != -1) {
            extname = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString() + extname;

        //获得输出流

        try {
            FileOutputStream outputStream = new FileOutputStream(new File(filePath, newFileName));
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Document document = new Document();

        document.setName(originalFilename);
        document.setCreateuser(ShiroUtil.getCurrentRealName());
        document.setFilename(newFileName);
        document.setContexttype(contentType);
        document.setType(Document.TYPE_DOC);
        document.setFid(fid);
        document.setSize(FileUtils.byteCountToDisplaySize(size));
        documentMapper.saveDir(document);

    }


    /**
     * 根据Id查询文件
     *
     * @param id
     * @return
     */
    public Document findDocById(Integer id) {
        return documentMapper.findDocById(id);
    }
}
