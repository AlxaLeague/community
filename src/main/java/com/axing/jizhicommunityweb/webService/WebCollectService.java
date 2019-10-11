package com.axing.jizhicommunityweb.webService;

import com.axing.entity.Collect;

import java.util.List;

public interface WebCollectService {
    List<Collect> getCollectByKuid(String uid);
    Collect selectCollect(String uid ,String thesisId);
    int deleteCollect(Integer collectId,String uid);
    int insertCollect(Collect collect);
}
