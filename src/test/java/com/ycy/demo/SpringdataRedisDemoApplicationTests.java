package com.ycy.demo;

import com.ycy.demo.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SpringdataRedisDemoApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Test
    void testString() {
        //插入数据 默认jdk序列化(存入redis乱码)
        redisTemplate.opsForValue().set("user:"+1,new User(1,"张三"));
//        redisTemplate.opsForValue().set("name","张三");
        //取出数据
        Object user = redisTemplate.opsForValue().get("user:"+1);
        System.out.println("user:"+user);
    }

}
