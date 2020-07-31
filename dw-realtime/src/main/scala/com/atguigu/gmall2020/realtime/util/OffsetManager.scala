package com.atguigu.gmall2020.realtime.util

import java.util

import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010.OffsetRange
import redis.clients.jedis.Jedis
import scala.collection.JavaConverters._


/**
 * @ClassName:OffsetManager
 * @Description:
 * @Author:Leon
 * @Date:2020/7/24 20:34
 *
 **/
object OffsetManager {

    /**
     * 从redis中读取offset
     * @param groupId
     * @param topic
     */
    def getOffset(groupId:String,topic:String): Map[TopicPartition, Long] ={
        val jedis: Jedis = RedisUtil.getJedisClient
        val offsetKey = topic + ":" + groupId

        var offsetMap: util.Map[String, String] = jedis.hgetAll(offsetKey)
        jedis.close()

        if(offsetMap != null && offsetMap.size()>0){
            val offsetList: List[(String, String)] = offsetMap.asScala.toList

            val offsetListForKafka: List[(TopicPartition, Long)] = offsetList.map {
                case (partition, offset) =>
                    val topicPartition = new TopicPartition(topic, partition.toInt)
                    println("加载偏移量：分区：" + partition + "==>" + offset)
                    (topicPartition, offset.toLong)
            }

            val offsetMapForKafka: Map[TopicPartition, Long] = offsetListForKafka.toMap

            offsetMapForKafka
        }else{
            null
        }

    }

    def saveOffset(topic:String,groupId:String,offsetRanges:Array[OffsetRange])={
        ///  redis  type? hash   key  ? 主题1：消费者组1  field ?  分区 value ?偏移量结束点
        val offsetKey: String = topic + ":" + groupId
        val offsetMap:util.Map[String,String]=new util.HashMap[String,String]() //用来存储多个分区的偏移量
        for (offsetRange <- offsetRanges ) {
            val partition: String = offsetRange.partition.toString
            val untilOffset: String = offsetRange.untilOffset.toString
            println("写入偏移量：分区："+partition+"==>"+untilOffset)
            offsetMap.put(partition,untilOffset)
        }
        val jedis: Jedis = RedisUtil.getJedisClient
        jedis.hmset(offsetKey,offsetMap)
        jedis.close()
    }
}