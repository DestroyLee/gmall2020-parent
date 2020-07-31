package com.atguigu.gmall2020.realtime.util

import java.io.InputStreamReader
import java.util.Properties

/**
 * @ClassName:PropertiesUtil
 * @Description:
 * @Author:Leon
 * @Date:2020/7/19 23:55
 *
 **/
object PropertiesUtil {

    def main(args: Array[String]): Unit = {
        val properties: Properties = PropertiesUtil.load("config.properties")

        println(properties.getProperty("kafka.broker.list"))

    }

    def load(propertiesName: String): Properties = {
        val properties = new Properties()
        properties.load(
            new InputStreamReader(
                Thread.currentThread().getContextClassLoader.getResourceAsStream(propertiesName),
                "UTF-8")
        )
        properties
    }
}
