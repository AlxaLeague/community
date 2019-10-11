package com.axing.jizhicommunityweb.webService;


import com.axing.entity.Topic;
import java.util.List;


public interface WebSolrService {
    List<Topic> selectByTitle(String q);
    int updateByThesis(Topic topic);
    int deleteByThesisId(String thesisId);
}
