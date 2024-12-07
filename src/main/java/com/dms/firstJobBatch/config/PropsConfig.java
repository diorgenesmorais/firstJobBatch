package com.dms.firstJobBatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PropsConfig {

	@Bean
	PropertySourcesPlaceholderConfigurer propertiesConfig() {
		var configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new FileSystemResource("/etc/config/batchs/application.properties"));
		return configurer;
	}
}
