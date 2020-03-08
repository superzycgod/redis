package cn.zycgod.example.redis.cluster.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;

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
		
		logger.info("开启自适应集群拓扑刷新和周期拓扑刷新");

		// 开启 自适应集群拓扑刷新和周期拓扑刷新
		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
				// 开启全部自适应刷新
				.enableAllAdaptiveRefreshTriggers() // 开启自适应刷新,自适应刷新不开启,Redis集群变更时将会导致连接异常
				// 自适应刷新超时时间(默认30秒)
				.adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30)) // 默认关闭开启后时间为30秒
				// 开周期刷新（刷新redis cluster集群活络拓扑信息，进行故障转移）
				.enablePeriodicRefresh(Duration.ofSeconds(20)) // 默认关闭开启后时间为60秒
																// ClusterTopologyRefreshOptions.DEFAULT_REFRESH_PERIOD
																// 60 .enablePeriodicRefresh(Duration.ofSeconds(2)) =
																// .enablePeriodicRefresh().refreshPeriod(Duration.ofSeconds(2))
				.build();

		// https://github.com/lettuce-io/lettuce-core/wiki/Client-Options
		ClientOptions clientOptions = ClusterClientOptions.builder()
				.topologyRefreshOptions(clusterTopologyRefreshOptions).build();
		
		clientConfigurationBuilder.clientOptions(clientOptions);

	}

}
