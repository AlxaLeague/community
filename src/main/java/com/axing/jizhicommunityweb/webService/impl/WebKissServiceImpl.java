package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.Kiss;
import com.axing.jizhicommunityweb.webService.WebKissService;
import com.axing.service.KissService;
import org.springframework.stereotype.Service;

@Service
public class WebKissServiceImpl implements WebKissService {
//    @Reference(loadbalance="roundrobin")
    @Reference
    KissService kissService;
    @Override
    public Kiss getKissByKuid(String kuid) {
        return kissService.getKissByKuid(kuid);
    }

    @Override
    public int updateKiss(Kiss kiss) {
        return kissService.updateKiss(kiss);
    }

    @Override
    public int insertKiss(Kiss kiss) {
        return kissService.insertKiss(kiss);
    }
}
