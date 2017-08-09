package com.company.project.web.content;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.infrastructure.tools.UploadUtils;
import com.company.project.model.News;
import com.company.project.service.NewsService;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
* Created by CodeGenerator on 2017/07/26.
*/
@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    @PostMapping("/add")
    public Result add(MultipartHttpServletRequest request) throws IOException {
        News news = new News();
        String title = request.getParameter( "title" );
        if (StringUtils.isNotEmpty( title )){
            news.setTitle( title );
        }else {
            return ResultGenerator.genFailResult( "请添加新闻标题!");
        }

        String content = request.getParameter( "editorValue" );
        if (StringUtils.isNotEmpty( content )){
            news.setContent( content );
        }else {
            return ResultGenerator.genFailResult( "新闻内容不能为空!");
        }

        String pictureName = UploadUtils.uploads( request);
        if (StringUtils.isNotEmpty( pictureName )){
            news.setPicture( pictureName );
        }else{
            return ResultGenerator.genFailResult( "需要上传新闻图片!");
        }

        newsService.save(news);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        newsService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(String id , String title, String picture , String content) {
        if (StringUtils.isEmpty( id )){
            return ResultGenerator.genFailResult("请选择一条要更新的新闻消息!");
        }else {
            News news = new News();
            news.setId( id );
            if (StringUtils.isNotEmpty( title )){
                news.setTitle( title );
            }
            if (StringUtils.isNotEmpty( picture )){
                news.setPicture( picture );
            }
            if (StringUtils.isNotEmpty( content )){
                news.setContent( content );
            }
            newsService.update(news);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        News news = newsService.findById(id);
        return ResultGenerator.genSuccessResult(news);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<News> list = newsService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    @PostMapping("/search")
    public Result search(String title,Date updateTimeMax , Date updateTimeMin) {
        News news = new News();
        if (StringUtils.isNotEmpty( title )){
            news.setTitle( title );
        }
        if (updateTimeMax!=null){
            news.setUpdateTimeMax( updateTimeMax );
        }
        if (updateTimeMin!=null){
            news.setUpdateTimeMin( updateTimeMin );
        }
        List<News> list = newsService.search( news );
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    /*
      Thymeleaf
      FreeMarker
      Velocity
      Groovy
      Mustache
   */


}
