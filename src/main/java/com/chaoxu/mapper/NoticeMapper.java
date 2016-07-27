package com.chaoxu.mapper;

import com.chaoxu.pojo.Notice;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/12.
 */
public interface NoticeMapper {


    /**添加公告
     * @param notice
     */
    void saveNotice(Notice notice);

    /**查询所有公告
     * @return
     */
    List<Notice> findAll();

    /**
     * 搜索查询公告
     * @param param
     * @return
     */
    List<Notice> findNoticeByParam(Map<String, Object> param);

    /**
     * 查询公告数据总数
     * @return
     */
    Long Count();

    /**
     * 查询限制的公告数据
     * @param param
     * @return
     */
    Long findFilterCount(Map<String, Object> param);

    /**
     * 根据公告ID查询公告内容
     * @param id
     * @return
     */
    Notice findNoticeById(Integer id);
}
