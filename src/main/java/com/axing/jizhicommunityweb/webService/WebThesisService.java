package com.axing.jizhicommunityweb.webService;

import com.axing.entity.Thesis;

import java.util.List;
import java.util.Map;

public interface WebThesisService {
    String addThesis(Map<String,String> map,String uid);
    String updateThesis(Map<String,String> map);
    Thesis getThesisByTid(String tid);
    List<Thesis> getThesis(Integer page, String clazz);
    int updateLook(String thesisId);
    int delThesisByTid(String thesisid);

    List<Thesis> getHotThesis();
    List<Thesis> getTopThesis(Integer size);
    List<Thesis> getThesisByUid(String uid);
}
