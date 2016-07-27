package com.chaoxu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * Created by dell on 2016/7/12.
 */
@Controller
public class FilePreviewController {

    @Value("${filePath}")
    private String filePath;
}
