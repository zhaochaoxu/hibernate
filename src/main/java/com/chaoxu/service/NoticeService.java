package com.chaoxu.service;

import com.chaoxu.mapper.NoticeMapper;
import com.chaoxu.pojo.Notice;
import com.chaoxu.util.ShiroUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dell on 2016/7/12.
 */
@Named
public class NoticeService {

    @Value("${filePath}")
    private String filePath;

    @Inject
    private NoticeMapper noticeMapper;

    public void SaveNotice(Notice notice) {
        notice.setRealname(ShiroUtil.getCurrentRealName());
        notice.setUserid(ShiroUtil.getCurrentUserID());
        noticeMapper.saveNotice(notice);
    }

    public List<Notice> findAll() {
        return noticeMapper.findAll();
    }

    public List<Notice> findNoticeByParam(Map<String, Object> param) {
        return noticeMapper.findNoticeByParam(param);
    }

    public Long Count() {
        return noticeMapper.Count();
    }

    public Long filterCount(Map<String, Object> param) {
        return noticeMapper.findFilterCount(param);
    }

    public Notice findNoticeById(Integer id) {
        return noticeMapper.findNoticeById(id);
    }


    public String saveImg(InputStream inputStream,String fileName) throws IOException {
        String extName = fileName.substring(fileName.lastIndexOf("."));
        String newfileName = UUID.randomUUID().toString()+extName;

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath,newfileName));
        IOUtils.copy(inputStream,fileOutputStream);


        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();

        return "/preview/"+newfileName;
    }
}
