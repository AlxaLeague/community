package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.Channel;
import com.axing.jizhicommunityweb.webService.WebChannelService;
import com.axing.service.ChannelService;
import com.axing.service.UserSercice;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WebChannelServiceImpl implements WebChannelService {
    @Reference
    ChannelService channelService;
    @Override
    public List<Channel> getChannel(int count) {
        return channelService.getChannel(count);
    }
}
