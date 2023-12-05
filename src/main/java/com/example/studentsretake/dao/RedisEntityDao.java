package com.example.studentsretake.dao;

import com.example.studentsretake.Entities.RedisEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisEntityDao {

    public static final String HASH_KEY = "Entity";

    @Autowired
    private RedisTemplate template;

    public RedisEntity save(RedisEntity entity){
        template.opsForHash().put(HASH_KEY, entity.getUserEmail(), entity);
        return entity;
    }


    public RedisEntity findEntityByUserEmail(String email){
        return (RedisEntity) template.opsForHash().get(HASH_KEY, email);
    }


}
