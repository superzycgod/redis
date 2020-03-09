package cn.zycgod.example.redis.sentinel.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;

import io.lettuce.core.ReadFrom;

/**
 * Springboot扩展接口<p>
 * 自义定一些LettuceClientConfiguration的配置，如开启自适应集群拓扑刷新和周期拓扑刷新
 * @author zhangyanchao
 *
 */
@Configuration
public class LettuceClientConfigurationCustomizer implements LettuceClientConfigurationBuilderCustomizer {
	
	private static final Logger logger = LoggerFactory.getLogger(LettuceClientConfigurationCustomizer.class);
	
	@Override
	public void customize(LettuceClientConfigurationBuilder clientConfigurationBuilder) {
		
		logger.info("设置读写分离模式：读操作先从slave中读，无可用slave则从master读");
		clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);

	}

}
