package cn.zycgod.example.redis.sentinel;

import org.junit.jupiter.api.Test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

public class DockerApiTests {
	
	@Test
	public void testInfo() {
		
		DockerClient dockerClient = DockerClientBuilder.getInstance().build();
		System.out.println(dockerClient.infoCmd().exec());
		
	}

}
