package com.a8tiyu.caizhan_kotlin.redis

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig


/**
 * @author aqhi
 * Created on 2017/10/19
 * Redis 配置
 */
@Configuration
class RedisConfig {

    internal var logger = LoggerFactory.getLogger(RedisConfig::class.java)

    @Value("\${spring.redis.host}")
    private val host: String? = "localhost"

    @Value("\${spring.redis.port}")
    private val port: Int = 0

    @Value("\${spring.redis.timeout}")
    private val timeout: Int = 0

    @Value("\${spring.redis.pool.max-idle}")
    private val maxIdle: Int = 0

    @Value("\${spring.redis.pool.max-wait}")
    private val maxWaitMillis: Long = 0

    @Value("\${spring.redis.password}")
    private val password: String? = null

    @Value("\${spring.redis.database}")
    private val database: Int = 0

    @Value("\${spring.redis.max-active}")
    private val ssl: Boolean = false


    /**
     * @ return JedisPool
     */
    @Bean
    fun redisPoolFactory(): JedisPool {
        logger.info("JedisPool注入成功！！")
        logger.info("redis地址：$host:$port")

        return JedisPool(getJedisPoolConfig(), host, port, timeout, password, database, ssl)
    }

    /**
     * JedisPool配置
     * @return JedisPoolConfig
     */
    fun getJedisPoolConfig(): JedisPoolConfig {
        val poolConfig = JedisPoolConfig()
        poolConfig.maxIdle = maxIdle
        poolConfig.maxWaitMillis = maxWaitMillis
        poolConfig.testOnBorrow = false
        poolConfig.testOnReturn = false
        poolConfig.testWhileIdle = false
        return poolConfig
    }
}