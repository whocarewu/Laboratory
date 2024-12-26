package com.cn.excelimporttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class RedisTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisTestApplication.class, args);
	}

}
