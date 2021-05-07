package com.kxj.config;

import lombok.Data;


/**
 * @author xiangjin.kong
 * @date 2021/5/7 16:07
 * @desc redis 池配置 {@link org.springframework.boot.autoconfigure.data.redis.RedisProperties}
 */
@Data
public class RedisPoolProperties {

    /**
     * 池中最大空闲连接数
     */
    private int maxIdle = 8;

    /**
     * 池中最小空闲连接数
     */
    private int minIdle = 0;

    /**
     * 最大连接数.
     */
    private int maxActive = 8;

    private int connTimeout;

    private int soTimeout;

    /**
     * 池大小
     */
    private  int size;

}
