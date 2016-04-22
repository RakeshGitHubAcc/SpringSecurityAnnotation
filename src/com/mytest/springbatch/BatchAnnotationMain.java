package com.mytest.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
@Configuration
//@EnableAutoConfiguration
@SpringBootApplication
public class BatchAnnotationMain {
	public static void main(String[] args) throws Exception{
		SpringApplication.run(BatchAnnotationMain.class, args);
	}

}
