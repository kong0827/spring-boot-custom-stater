package com.kxj.config;

import com.google.common.base.Strings;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiangjin.kong
 * @date 2021/5/7 15:49
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";

    @Autowired
    private RedissonProperties redissonProperties;


    /**
     * {@link org.redisson.Redisson}
     *  public static RedissonClient create() {
     *         Config config = new Config();
     *         config.useSingleServer()
     *         .setTimeout(1000000)
     *         .setAddress("redis://127.0.0.1:6379");
     * //        config.useMasterSlaveConnection().setMasterAddress("127.0.0.1:6379").addSlaveAddress("127.0.0.1:6389").addSlaveAddress("127.0.0.1:6399");
     * //        config.useSentinelConnection().setMasterName("mymaster").addSentinelAddress("127.0.0.1:26389", "127.0.0.1:26379");
     * //        config.useClusterServers().addNodeAddress("127.0.0.1:7000");
     *         return create(config);
     *     }
     */

    /**
     * 单机配置
     */
    @Bean
    @ConditionalOnProperty(name="spring.redis.mode", havingValue = "single")
    RedissonClient redissonClientSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setDatabase(redissonProperties.getDatabase())
                .setTimeout(redissonProperties.getTimeout())
                .setAddress(REDIS_PROTOCOL_PREFIX.concat(redissonProperties.getSingle().getAddress()));
        if (Strings.isNullOrEmpty(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 哨兵配置
     */
    @Bean
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "sentinel")
    RedissonClient redissonClientSentinel() {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(redissonProperties.getSentinel().getSentinelAddresses().stream()
                        .map(address -> address.startsWith(REDIS_PROTOCOL_PREFIX) ? address : REDIS_PROTOCOL_PREFIX.concat(address)).toArray(String[]::new))
                .setDatabase(redissonProperties.getDatabase())
                .setMasterName(redissonProperties.getSentinel().getMasterName())
                .setTimeout(redissonProperties.getTimeout());
        if (Strings.isNullOrEmpty(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 集群模式的 redisson 客户端
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "cluster")
    RedissonClient redissonCluster() {
        Config config = new Config();
        ClusterServersConfig serverConfig = config.useClusterServers()
                .addNodeAddress(redissonProperties.getCluster().getNodeAddresses().stream()
                        .map(address -> address.startsWith(REDIS_PROTOCOL_PREFIX) ? address : REDIS_PROTOCOL_PREFIX.concat(address)).toArray(String[]::new))
                .setTimeout(redissonProperties.getTimeout())
                .setScanInterval(redissonProperties.getCluster().getScanInterval());
        if (Strings.isNullOrEmpty(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 主从
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "master-salve")
    RedissonClient redissonClientMasterSalve() {
        Config config = new Config();
        MasterSlaveServersConfig serverConfig = config.useMasterSlaveServers()
                .setDatabase(redissonProperties.getDatabase())
                .setMasterAddress(redissonProperties.getMasterSlave().getMasterAddress())
                .addSlaveAddress(redissonProperties.getCluster().getNodeAddresses().stream()
                        .map(node -> node.startsWith(REDIS_PROTOCOL_PREFIX) ? node : REDIS_PROTOCOL_PREFIX.concat(node)).toArray(String[]::new));
        if (Strings.isNullOrEmpty(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
