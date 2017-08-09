package com.company.project.service;
import com.company.project.model.News;
import com.company.project.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2017/07/26.
 */
public interface NewsService extends Service<News> {
    List<News> search(News news);
}
