package com.mjc.realtime;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.mjc.realtime.dao.mapper")
public class RealtimeApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(RealtimeApplication.class, args);
	}
//	 extends SpringBootServletInitializer

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RealtimeApplication.class);
    }
}
