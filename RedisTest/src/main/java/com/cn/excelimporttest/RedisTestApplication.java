package com.cn.excelimporttest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

@Slf4j
@SpringBootApplication
public class RedisTestApplication {
	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(RedisTestApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("local.server.port");
		String path = Objects.toString(env.getProperty("server.servlet.context-path"), "").trim();
		log.info("\n----------------------------------------------------------\n\t" +
				"Application Demo is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + path + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + path + "/\n\t" +
				"Swagger文档:\thttp://" + ip + ":" + port + path + "/swagger-ui/index.html#/\n" +
				"----------------------------------------------------------");
	}

}
