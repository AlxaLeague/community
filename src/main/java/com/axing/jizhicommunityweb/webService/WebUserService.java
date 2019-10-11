package com.axing.jizhicommunityweb.webService;

import com.axing.entity.UserInfo;

public interface WebUserService {
    UserInfo getUserByUid(String uid);
    UserInfo loginByEmailAndPass(String email,String pass);
    int register(String email, String username ,String pass);
    UserInfo getUserByEmail(String email);
    int changePass(Integer id,String pass);
    int updateUserInfoAvatar(Integer id,String avatar);
    int updateUserInfoSet(Integer id,String username , Integer sex, String city, String sign);

}
