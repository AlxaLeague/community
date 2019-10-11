package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.Advertising;
import com.axing.jizhicommunityweb.webService.WebAdvertisingService;
import com.axing.service.AdvertisingService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WebAdvertisingServiceImpl implements WebAdvertisingService {
    @Reference
    AdvertisingService advertisingService;
    @Override
    public List<Advertising> getAdvertising() {
        return advertisingService.getAdvertising("");
    }
}
