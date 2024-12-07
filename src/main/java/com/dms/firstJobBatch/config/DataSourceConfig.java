package com.dms.firstJobBatch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfig {

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	DataSource springDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "app.datasource")
	DataSource appDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	PlatformTransactionManager transactionManagerApp(@Qualifier("appDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
