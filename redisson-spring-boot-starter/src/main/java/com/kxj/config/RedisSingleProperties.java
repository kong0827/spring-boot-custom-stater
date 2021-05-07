package com.kxj.config;

import lombok.Data;

/**
 * @author xiangjin.kong
 * @date 2021/5/7 16:15
 * @desc 单机配置   {@link org.springframework.boot.autoconfigure.data.redis.RedisProperties}
 */
@Data
public class RedisSingleProperties {

    private String address;
}
