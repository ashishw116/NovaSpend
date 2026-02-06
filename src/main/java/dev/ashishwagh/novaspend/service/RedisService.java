package dev.ashishwagh.novaspend.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;
	public  <T> T get(String key,TypeReference<T> typeRef)
	{
		Object obj=redisTemplate.opsForValue().get(key);
		return obj==null?null:objectMapper.convertValue(obj,typeRef);
	}
	public void set(String key,Object obj,Long ttl)
	{
		redisTemplate.opsForValue().set(key, obj, ttl, TimeUnit.MINUTES);
	}
	public void deleteByPattern(String pattern)
	{
		Set<String> keys=redisTemplate.keys(pattern);
		if(keys!=null&&!keys.isEmpty())
		{
			redisTemplate.delete(keys);
		}
	}
	public void delete(String keys) {
		if(keys!=null&&!keys.isEmpty())
		{
			redisTemplate.delete(keys);
		}
		
	}
}
