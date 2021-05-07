package com.kxj.config;

import lombok.Data;
import org.redisson.config.MasterSlaveServersConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xiangjin.kong
 * @date 2021/5/7 16:32
 * @desc 主从配置 {@link MasterSlaveServersConfig}
 */
@Data
public class RedissonMasterSlaveProperties {
    /**
     * Redis slave servers addresses
     */
    private Set<String> slaveAddresses = new HashSet<String>();

    /**
     * Redis master server address
     */
    private String masterAddress;
}
