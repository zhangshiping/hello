package com.company.project.web.content;

import com.company.project.infrastructure.tools.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 *
 * Created by zhangshiping on 2017/7/28 0028.
 */
@Controller
public class FileController {

    //文件上传
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(MultipartHttpServletRequest request) {
        System.out.println("upload file");
        return UploadUtils.upload(request);
    }

    //多文件上传
    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        return UploadUtils.uploads(request);
    }

}
