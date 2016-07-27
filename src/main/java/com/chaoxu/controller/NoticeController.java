package com.chaoxu.controller;

import com.chaoxu.dto.DataTablesResult;
import com.chaoxu.exception.NoFoundException;
import com.chaoxu.pojo.Notice;
import com.chaoxu.service.NoticeService;
import com.chaoxu.util.Strings;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/12.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {


    @Inject
    private NoticeService noticeService;


    @RequestMapping(method = RequestMethod.GET)
    public String list(){
        return "notice/list";

    }


    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Notice> shownotice(HttpServletRequest request){

        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");
        keyword = Strings.toUTF8(keyword);

        Map<String,Object> param = Maps.newHashMap();

        param.put("start",start);
        param.put("length",length);
        param.put("keyword",keyword);

        List<Notice> noticeList = noticeService.findNoticeByParam(param);

        Long count = noticeService.Count();
        Long filterCount = noticeService.filterCount(param);

        return new DataTablesResult<>(draw,noticeList,count,filterCount);


    }
    /**
     * 查询所有公告
     * @return
     *//*
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> showNotice() {

        Map<String,Object> param = Maps.newHashMap();

        param.put("data",noticeService.findAll());

        return param;

    }*/

    /**
     * 发布公告
     *
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newNOtice() {
        return "notice/new";
    }

    /**
     * 保存公告
     *
     * @param notice
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String SaveNotice(Notice notice, RedirectAttributes redirectAttributes) {
        noticeService.SaveNotice(notice);

        redirectAttributes.addFlashAttribute("message", "发表成功");
        return "redirect:/notice" ;
    }

    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.GET)
    public String viewNotice(@PathVariable Integer id, Model model){
        Notice notice = noticeService.findNoticeById(id);
        if(notice==null){
            throw new NoFoundException();
        }
        model.addAttribute("notice",notice);
        return "notice/view";

    }

    /**
     * 在线编译器上传图片
     * @return
     */
    @RequestMapping(value = "/img/upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> upload(MultipartFile file) throws IOException {

        Map<String,Object> result = Maps.newHashMap();
        if(!file.isEmpty()){
            String filePath = noticeService.saveImg(file.getInputStream(),file.getOriginalFilename());
            result.put("success",true);
            result.put("file_path",filePath);
        }else{
            result.put("success",false);
            result.put("msg","请选择文件");
        }
        return result;
    }
}
