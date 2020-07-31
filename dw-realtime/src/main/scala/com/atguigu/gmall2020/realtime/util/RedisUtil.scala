package com.atguigu.gmall2020.realtime.util

import java.util.Properties

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
 * @ClassName:RedisUtil
 * @Description:
 * @Author:Leon
 * @Date:2020/7/24 20:36
 *
 **/
object RedisUtil {


    var jedisPool: JedisPool = null

    def getJedisClient={
        if (jedisPool==null){
            // 创建新的连接池

            val conf: Properties = PropertiesUtil.load("config.properties")
            val host: String = conf.getProperty("redis.host")
            val port: String = conf.getProperty("redis.port")

            val jedisPoolConfig = new JedisPoolConfig()
            jedisPoolConfig.setMaxIdle(20)  // 最大空闲数
            jedisPoolConfig.setMaxTotal(100)    // 最大连接数
            jedisPoolConfig.setMinIdle(20)  // 最小空闲数
            jedisPoolConfig.setBlockWhenExhausted(true) // 忙碌时是否等待
            jedisPoolConfig.setMaxWaitMillis(500)   // 忙碌时等待时间 毫秒
            jedisPoolConfig.setTestOnBorrow(true)   // 每次获得连接时测试

            jedisPool = new JedisPool(jedisPoolConfig, host, port.toInt)

        }
        jedisPool.getResource
    }


}
