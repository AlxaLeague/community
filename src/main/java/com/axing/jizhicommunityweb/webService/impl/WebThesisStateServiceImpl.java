package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.Thesis;
import com.axing.entity.Thesisstate;
import com.axing.jizhicommunityweb.webService.WebThesisService;
import com.axing.jizhicommunityweb.webService.WebThesisStateService;
import com.axing.service.ThesisService;
import com.axing.service.ThesisstateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WebThesisStateServiceImpl implements WebThesisStateService {

    @Reference
    ThesisstateService thesisstateService;


    @Override
    public Thesisstate getThesisstateBytid(String thesisid) {
        return thesisstateService.getThesisstateBytid(thesisid);
//        return null;
    }
}
