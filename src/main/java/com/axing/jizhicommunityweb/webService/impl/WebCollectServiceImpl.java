package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.Collect;
import com.axing.jizhicommunityweb.webService.WebCollectService;
import com.axing.service.CollectService;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class WebCollectServiceImpl implements WebCollectService {
    @Reference
    CollectService collectService;
    @Override
    public List<Collect> getCollectByKuid(String uid) {
        return collectService.getCollectByKuid(uid);
    }

    @Override
    public Collect selectCollect(String uid, String thesisId) {
        return collectService.selectCollect(uid,thesisId);
    }

    @Override
    public int deleteCollect(Integer collectId,String uid) {
        return collectService.deleteCollect(collectId,uid);
    }

    @Override
    public int insertCollect(Collect collect) {
        return collectService.insertCollect(collect);
    }
}
