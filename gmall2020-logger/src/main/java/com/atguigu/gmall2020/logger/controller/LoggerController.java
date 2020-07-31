/**
 * Copyright (C), 2020, 尚硅谷
 * FileName: LoggerController
 * Author:   Leon
 * Date:     2020/7/16 21:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.atguigu.gmall2020.logger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Leon
 * @create 2020/7/16
 * @since 1.0.0
 */

@RestController    //=@controller @response
@Slf4j
public class LoggerController {

    @Autowired
    KafkaTemplate kafkaTemplate;


    @RequestMapping("/applog")
    public String applog(@RequestBody String json){
        System.out.println(json);
        JSONObject jsonObject = JSON.parseObject(json);


        if(jsonObject.getString("start")!=null &&jsonObject.getString("start").length()>0 ){
            kafkaTemplate.send("GMALL_START",json);
        }else{
            kafkaTemplate.send("GMALL_EVENT",json);
        }

        log.info(json);


        return "ok";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "hello demo";
    }


}
