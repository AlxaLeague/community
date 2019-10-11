package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.Thesis;
import com.axing.entity.Topic;
import com.axing.jizhicommunityweb.webService.WebThesisService;
import com.axing.service.SolrService;
import com.axing.service.ThesisService;
import com.axing.service.UserSercice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class WebThesisServiceImpl implements WebThesisService {

    @Reference
    ThesisService thesisService;

    @Reference
    SolrService solrService;

    @Override
    public String addThesis(Map<String,String> map,String uid) {
        String thesisid;
        String clazz = map.get("clazz");
        String title = map.get("title");
        Integer experionce = Integer.parseInt(map.get("experionce"));
        String timestamp = ""+System.currentTimeMillis()/1000;
        Integer look = 0;
        Integer commentcount = 0;
        String content = map.get("content");
        String contenthtml = map.get("contenthtml");
        Thesis thesis = new Thesis();
        thesis.setTitle(title);
        thesis.setTimestamp(timestamp);
        thesis.setExperionce(experionce);
        thesis.setLook(look);
        thesis.setCommentcount(commentcount);
        thesis.setClazz(clazz);
        thesis.setUid(uid);
        thesis.setContent(content);
        thesis.setContenthtml(contenthtml);
        String s = thesisService.addThesis(thesis);
        solrService.updateByThesis(new Topic(s,thesis.getClazz(),thesis.getTitle(),thesis.getExperionce(),thesis.getTimestamp(),
                thesis.getLook(),0,thesis.getUid(),"","",""));
        return s;
    }

    @Override
    public String updateThesis(Map<String, String> map) {
        String thesisid = map.get("thesisid");
        String clazz = map.get("clazz");
        String title = map.get("title");
        Integer experionce = Integer.parseInt(map.get("experionce"));
        String timestamp = ""+System.currentTimeMillis()/1000;
        String project;
        String version;
        String browser;
        String content = map.get("content");
        String contenthtml = map.get("contenthtml");
        Thesis thesis = new Thesis();
        thesis.setTitle(title);
        thesis.setTimestamp(timestamp);
        thesis.setExperionce(experionce);
        thesis.setClazz(clazz);
        thesis.setThesisid(thesisid);
        thesis.setContent(content);
        thesis.setContenthtml(contenthtml);
        System.out.println(90909090);
//        thesisService.getThesisByTid(thesisid);
        thesisService.updateThesisBytid(thesis);
        solrService.updateByThesis(new Topic(thesis.getThesisid(),thesis.getClazz(),thesis.getTitle(),thesis.getExperionce(),thesis.getTimestamp(),
                thesis.getLook(),0,thesis.getUid(),"","",""));
        return thesisid;
    }

    @Override
    public Thesis getThesisByTid(String tid) {
        return thesisService.getThesisByTid(tid);
    }

    @Override
    public List<Thesis> getThesis(Integer page, String clazz) {
        return thesisService.getThesis(page,clazz);
    }

//    @Override
//    public List<Thesis> getThesis(String clazz, int start, int pagesize) {
//        return null;
//    }

    @Override
    public int updateLook(String thesisId) {
        Thesis thesis = new Thesis();
        Thesis thesis1 = thesisService.getThesisByTid(thesisId);
        thesis.setId(thesis1.getId());
        thesis.setLook(thesis1.getLook()+1);
        return thesisService.updateThesis(thesis);
    }

    @Override
    public int delThesisByTid(String thesisid) {
        return thesisService.delThesisByTid(thesisid);
    }

    @Override
    public List<Thesis> getHotThesis() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK,-1);
        String time = "" + (c.getTimeInMillis()/1000);
        System.out.println(time);
        return thesisService.getHotThesis(time);
    }

    @Override
    public List<Thesis> getTopThesis(Integer size) {
        return thesisService.getTopThesis(size);
    }

    @Override
    public List<Thesis> getThesisByUid(String uid) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Thesis> thesisList = thesisService.getThesisByUid(uid);
        for (Thesis thesis : thesisList){

            Date date = new Date(Long.parseLong(thesis.getTimestamp())*1000);
            String timestamp = simpleDateFormat.format(date);
            thesis.setTimestamp(timestamp);
        }
        return thesisList;
    }


}
