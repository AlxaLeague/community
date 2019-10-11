package com.axing.jizhicommunityweb.webService;

public interface VercodeService {
    String getLoginCode();
    Boolean CheckLoginCode(String code);
    String getRegCode();
    Boolean CheckRegCode(String code);
    String sendForgetCode(String email);
    Boolean CheckForgetCode(String email,String code);
}
