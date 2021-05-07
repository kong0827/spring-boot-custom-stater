package com.kxj.config;

import lombok.Data;
import org.redisson.config.SentinelServersConfig;

import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2021/5/7 16:29
 * @desc 哨兵配置 {@link SentinelServersConfig}
 */
@Data
public class RedissonSentinelProperties {

    private List<String> sentinelAddresses;

    private String masterName;

    /**
     * Sentinel scan interval in milliseconds
     */
    private int scanInterval = 1000;

}
