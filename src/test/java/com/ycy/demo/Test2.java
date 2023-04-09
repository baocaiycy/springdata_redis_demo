package com.ycy.demo;

import com.alibaba.fastjson.JSON;

import com.ycy.demo.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Test2 {

    @Autowired
    //默认key hashKey value hashValue都为String类型
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testString() {
        //插入数据
        stringRedisTemplate.opsForValue().set("name","李四");
//        redisTemplate.opsForValue().set("name","张三");
        //取出数据
        String name = stringRedisTemplate.opsForValue().get("name");
        System.out.println("name:"+name);
    }

    @Test
    void testSetObject() {
        User user = new User(101, "张三");

        //手动序列化
        String jsonString = JSON.toJSONString(user);
        //插入数据
        stringRedisTemplate.opsForValue().set("user:"+user.getId(),jsonString);

        //取出数据
        String userString = stringRedisTemplate.opsForValue().get("user:"+user.getId());
        //手动反序列
        User userResult = JSON.parseObject(userString, User.class);
        System.out.println("user:"+userResult);
    }

    @Test
    void testHash(){
        Map<String,Object> map = new HashMap<>();
//        map.put("user:"+1,new User(1,"张三"));
//        map.put("user:"+2,new User(2,"王二"));
//        map.put("user:"+3,new User(3,"李四"));
        map.put("name","张三");
        map.put("age","18");
        //序列化
//        String jsonString = JSON.toJSONString(map);
        //插入数据
        stringRedisTemplate.opsForHash().putAll("user_hash",map);
        //取出数据
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries("user_hash");
        userMap.entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey()+":"+entry.getValue()));

    }
}
