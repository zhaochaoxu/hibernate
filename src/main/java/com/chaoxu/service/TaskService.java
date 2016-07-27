package com.chaoxu.service;

import com.chaoxu.mapper.TaskMapper;
import com.chaoxu.pojo.Task;
import com.chaoxu.util.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by dell on 2016/7/18.
 */
@Named
public class TaskService {

    @Inject
    private TaskMapper taskMapper;

    /**
     * 新建待办事项
     * @param task
     * @param hour
     * @param min
     */
    public void saveTask(Task task, String hour, String min) {

        if(StringUtils.isNotEmpty(hour)&&StringUtils.isNotEmpty(min)){
            String reminderTime = task.getStart()+" "+hour+":"+min+":00";
            task.setRemindertime(reminderTime);
        }
        task.setUserid(ShiroUtil.getCurrentUserID());
        taskMapper.save(task);

    }

    /**
     * 删除日程
     * @param id
     */
    public void del(Integer id) {
        taskMapper.del(id);
    }

    /**
     * 获取当前用户已超时的任务
     * @return
     */
    public List<Task> findTimeOutTasks() {

        String today = DateTime.now().toString("yyyy-MM-dd");
        return taskMapper.findTimeOutTask(ShiroUtil.getCurrentUserID(),today);

    }


    /**
     * 获取当前用户的所有任务
     * @return
     */
    public List<Task> findTaskByUserId(String start,String end) {
        return taskMapper.findByUserIdAndDateRanger(ShiroUtil.getCurrentUserID(),start,end);
    }

    /**
     * 将日设置为已完成
     * @param id
     * @return
     */
    public Task doneTask(Integer id){
        Task task = taskMapper.findById(id);
        task.setDone(true);
        task.setColor("#cccccc");

        taskMapper.update(task);
        return task;
    }

}
