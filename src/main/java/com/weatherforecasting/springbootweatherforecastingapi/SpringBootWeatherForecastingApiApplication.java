package com.weatherforecasting.springbootweatherforecastingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.weatherforecasting"})
public class SpringBootWeatherForecastingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWeatherForecastingApiApplication.class, args);
	}

}
