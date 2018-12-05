package com.meeting.meetresv.utils.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meeting.meetresv.pojo.MrUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String key, Long time, MrUser user) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<MrUser> users) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(users), time, TimeUnit.MINUTES);
    }

    public MrUser get(String key) {
        Gson gson = new Gson();
        MrUser user = null;
        String userJson = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(userJson)) {
            user = gson.fromJson(userJson, MrUser.class);
        }
        return user;
    }

    public List<MrUser> getList(String key) {
        Gson gson = new Gson();
        List<MrUser> ts = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(listJson)) {
            ts = gson.fromJson(listJson, new TypeToken<List<MrUser>>(){}.getType());
        }
        return ts;
    }

    public void delete(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
