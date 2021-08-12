package com.w2m.superheroes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.w2m.superheroes.config.ConfiguradorSpring;
import com.w2m.superheroes.service.HeroeService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@Configuration
//@EnableDiscoveryClient
@Slf4j
public class SuperheroesApiApplication {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SuperheroesApiApplication.class, args);
	}
	
//	 AnnotationConfigApplicationContext ctx = new  AnnotationConfigApplicationContext(ConfiguradorSpring.class);
//	    HeroeService servicio= ctx.getBean(HeroeService.class);
	    

}
