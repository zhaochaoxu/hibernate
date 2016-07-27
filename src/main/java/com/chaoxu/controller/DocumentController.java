package com.chaoxu.controller;

import com.chaoxu.exception.NoFoundException;
import com.chaoxu.pojo.Document;
import com.chaoxu.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;

/**
 * Created by dell on 2016/7/13.
 */

@Controller
@RequestMapping("/doc")
public class DocumentController {


    @Inject
    private DocumentService documentService;
    @Value("${filePath}")
    private String filePath;


    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model,
                       @RequestParam(required = false, defaultValue = "0") Integer fid) {

        model.addAttribute("documentlist", documentService.findDocByFid(fid));

        model.addAttribute("fid", fid);
        return "document/list";
    }

    /**
     * 保存新文件夹
     *
     * @param name 文件夹的名字
     * @param fid  文件夹的级别
     * @return
     */
    @RequestMapping(value = "/dir/new", method = RequestMethod.POST)
    public String saveDir(String name, Integer fid) {

        Document document = new Document();
        document.setName(name);
        document.setFid(fid);
        documentService.saveDir(document);
        return "redirect:/doc?fid=" + fid;
    }

    /**
     * 上传文件
     *
     * @param file
     * @param fid
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public String saveFile(MultipartFile file, Integer fid) throws IOException {

        if (file.isEmpty()) {
            throw new NoFoundException();
        }
        documentService.saveFile(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), file.getSize(), fid);
        return "success";
    }


    @RequestMapping(value = "/download/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> uploadFile(@PathVariable Integer id) throws FileNotFoundException, UnsupportedEncodingException {
        Document document = documentService.findDocById(id);
        if (document == null) {
            throw new NoFoundException();
        }
        File file = new File(filePath, document.getFilename());
        if (!file.exists()) {
            throw new NoFoundException();
        }

        FileInputStream inputStream = new FileInputStream(file);
        String fileName = document.getName();
        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getContexttype()))
                .contentLength(file.length())
                .header("Content-Disposition", "attachment;filename=\"" + fileName + "\"")
                .body(new InputStreamResource(inputStream));
    }
}
