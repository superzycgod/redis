package cn.zycgod.example.redis.sentinel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class RedisSentinelApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void contextLoads() {
		
		set("name1", "张三");
		set("name2", "李四");
		set("name3", "王二麻");
		
		get("name");
		get("name1");
		get("name2");
		get("name3");
		
		
	}
	
	private void set(String key ,String val) {
		stringRedisTemplate.opsForValue().set(key, val);
	}
	
	private String get(String key) {
		String val = stringRedisTemplate.opsForValue().get(key);
		System.out.println(String.format("%s = %s", key, val));
		return val;
	}

}
