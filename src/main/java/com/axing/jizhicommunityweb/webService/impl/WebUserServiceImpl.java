package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.UserInfo;
import com.axing.jizhicommunityweb.webService.WebUserService;
import com.axing.service.UserSercice;
import org.springframework.stereotype.Service;

@Service
public class WebUserServiceImpl implements WebUserService {
    @Reference
    UserSercice userSercice;

    @Override
    public UserInfo getUserByUid(String uid) {
        UserInfo userInfo = userSercice.getUserByUid(uid);
        System.out.println(userInfo.toString());
        return userInfo;
    }

    @Override
    public UserInfo loginByEmailAndPass(String email, String pass) {
        return userSercice.loginByEmailAndPass(email,pass);
    }


    @Override
    public int register(String email, String username, String pass) {
        return userSercice.register(email,username,pass);
    }

    @Override
    public UserInfo getUserByEmail(String email) {
        UserInfo userInfo = userSercice.getUserByEmail(email);
        System.out.println(userInfo.toString());
        return userInfo;
    }

    @Override
    public int changePass(Integer id, String pass) {
        int i =userSercice.changePass(id,pass);
        return i;
    }

    @Override
    public int updateUserInfoAvatar(Integer id,String avatar) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setAvatar(avatar);
        return userSercice.updateUserInfo(userInfo);
    }

    @Override
    public int updateUserInfoSet(Integer id, String username, Integer sex, String city, String sign) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setSex(sex);
        userInfo.setCity(city);
        userInfo.setSignature(sign);
        userInfo.setId(id);
        return userSercice.updateUserInfo(userInfo);

    }
}
