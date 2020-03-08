package cn.zycgod.example.redis.cluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RedisClusterApplication {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@RequestMapping("/get/{key}")
	public String get(@PathVariable("key") String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisClusterApplication.class, args);
	}

}
