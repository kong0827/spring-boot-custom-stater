package com.kxj.config;

import lombok.Data;
import org.redisson.config.ClusterServersConfig;

import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2021/5/7 16:17
 * @desc 集群配置 {@link ClusterServersConfig}
 */
@Data
public class RedissonClusterProperties {
    /**
     * 集群状态扫描间隔时间，单位是毫秒
     */
    private int scanInterval = 5000;
    /**
     * 集群节点
     */
    private List<String> nodeAddresses;

}
