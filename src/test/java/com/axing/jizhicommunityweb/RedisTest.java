package com.axing.jizhicommunityweb;


import com.axing.jizhicommunityweb.entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;


import org.junit.Assert;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "12211");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        //User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        ValueOperations<String,String> operations=redisTemplate.opsForValue();
        operations.set("com.neox", "Spring");
        operations.set("com.neo.f", "springboot",1, TimeUnit.SECONDS);
        System.out.println(operations.get("com.neox"));
        Thread.sleep(100);
        //redisTemplate.delete("com.neo.f");
//        boolean exists=redisTemplate.hasKey("com.neo.f");
        boolean exists=redisTemplate.hasKey("user-key::com.neo.controller.TestControllergetUser");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
    @Test
    public void testuserkey() throws Exception {
        UserInfo user=new UserInfo(1,"2","3","4","5","6","7","8","","9","10");
        ValueOperations<String, Object> operations=redisTemplate.opsForValue();
        operations.set("com.neox", user);
        operations.set("com.neo.f", user,1,TimeUnit.SECONDS);
        System.out.println(operations.get("com.neox"));
        Thread.sleep(1000);
        //redisTemplate.delete("com.neo.f");
        boolean exists=redisTemplate.hasKey("com.neo.f");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }


}