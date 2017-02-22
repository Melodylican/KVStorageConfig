package com.dsky.kv.configservice.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.dsky.kv.configservice.logservice.IWarningReporterService;

import redis.clients.jedis.exceptions.JedisConnectionException;


public class RedisMethonds {
	private static Logger logger = Logger.getLogger(RedisMethonds.class);
	private static RedisTemplate redisTemplate;
	
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	//更新后删除redis上对应的活动配置信息的key
	
	public static  void delActivitieKey(String key) {
		try{
			if(redisTemplate.hasKey(key)) {
		           redisTemplate.delete(key);
		           logger.info("调用delActivitieKey 删除 : "+key);
		     }else {
		    	 logger.info("Redis 中没有对应的 Key : "+key);
		     }
			
		}catch (JedisConnectionException e) {
			logger.error("redis连接出现异常 "+e.getMessage(), e);
			return;
		}
	}
}
