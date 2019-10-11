package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.jizhicommunityweb.webService.VercodeService;
import com.axing.service.EmailSercice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
@Service
public class VercodeServiceImpl implements VercodeService {

    @Reference
    EmailSercice emailSercice;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired

    @Override
    public String getLoginCode() {
//        HashOperations<String,String,Object> opsForHash=redisTemplate.opsForHash();
        String code = verifyCode();
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        valueOperations.set("Login_"+code, code,3, TimeUnit.MINUTES);
        return code;
    }

    @Override
    public Boolean CheckLoginCode(String code) {
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        boolean b = false;
        String s = valueOperations.get("Login_"+code);
        if (StringUtils.isNotBlank(s) && code.equals(s)){
            b =true;
        }
        return b;
    }

    @Override
    public String getRegCode() {
        String code = verifyCode();
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        valueOperations.set("Reg_"+code, code,3, TimeUnit.MINUTES);
        return code;
    }

    @Override
    public Boolean CheckRegCode(String code) {
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        boolean b = false;
        String s = valueOperations.get("Reg_"+code);
        if (StringUtils.isNotBlank(s) && code.equals(s)){
            b =true;
        }
        return b;
    }

    @Override
    public String sendForgetCode(String email) {
        String code = verifyCode();
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        valueOperations.set("Forget_"+email, code,3, TimeUnit.MINUTES);
        //发送邮件
        emailSercice.sendSimpleMail(email,"验证码",code);
        return code;
    }

    @Override
    public Boolean CheckForgetCode(String email,String code) {
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        boolean b = false;
        String s = valueOperations.get("Forget_"+email);
        System.out.println(s);
        if (StringUtils.isNotBlank(s) && code.equals(s)){
            System.out.println(s);
            b =true;
        }
        return b;
    }

    public static String verifyCode(){
        String str = "";
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Random random = new Random();
        for (int i = 0; i <4; i++){
            char num = ch[random.nextInt(ch.length)];
            str += num;
        }
        return str;
    }
}
