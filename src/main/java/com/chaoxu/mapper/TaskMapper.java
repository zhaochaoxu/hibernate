package com.chaoxu.mapper;

import com.chaoxu.pojo.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dell on 2016/7/18.
 */
public interface TaskMapper {

    void save(Task task);

    void update(Task task);

    void del(Integer id);

    Task findById(Integer id);

    List<Task> findTimeOutTask(@Param("userid") Integer userid,@Param("today") String today);

    List<Task> findByUserId(@Param("userid")Integer userid, @Param("start")String start, @Param("end")String end);

    List<Task> findByUserIdAndDateRanger(@Param("userid")Integer userid, @Param("start")String start, @Param("end")String end);
}
