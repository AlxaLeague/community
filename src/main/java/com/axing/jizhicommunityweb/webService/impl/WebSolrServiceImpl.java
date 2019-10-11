package com.axing.jizhicommunityweb.webService.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.Topic;
import com.axing.jizhicommunityweb.webService.WebSolrService;
import com.axing.service.SolrService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSolrServiceImpl implements WebSolrService {

    @Reference
    SolrService solrService;

    @Override
    public List<Topic> selectByTitle(String q) {
        return solrService.selectByTitle(q);
    }

    @Override
    public int updateByThesis(Topic topic) {
        return solrService.updateByThesis(topic);
    }

    @Override
    public int deleteByThesisId(String thesisId) {
        return solrService.deleteByThesisId(thesisId);
    }
}
