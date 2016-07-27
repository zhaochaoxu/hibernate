package com.chaoxu.controller;

import com.chaoxu.dto.JSONresult;
import com.chaoxu.pojo.Task;
import com.chaoxu.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dell on 2016/7/18.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Inject
    private TaskService taskService;


    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, @RequestParam(required = false,defaultValue = "")String start,
                       @RequestParam(required = false,defaultValue = "") String end) {

         List<Task> timeoutTaskList = taskService.findTimeOutTasks();
         model.addAttribute("outTask",timeoutTaskList);

        return "task/list";

    }

    /**
     * 添加新日程
     *
     * @param task
     * @param hour
     * @param min
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONresult save(Task task, String hour, String min) {
        taskService.saveTask(task, hour, min);
        return new JSONresult(task);
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> load(String start, String end) {
        return taskService.findTaskByUserId(start, end);
    }

    /**
     * 删除日程
     *
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public String delTask(@PathVariable Integer id) {
        taskService.del(id);
        return "success";
    }

    /**
     * 将日志标记为已完成
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}/done",method = RequestMethod.POST)
    @ResponseBody
    public JSONresult doneTask(@PathVariable Integer id) {
        Task task = taskService.doneTask(id);
        return new JSONresult(task);

    }





















}
