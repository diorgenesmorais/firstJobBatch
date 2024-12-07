package com.dms.firstJobBatch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.dms.firstJobBatch.model.Pessoa;

@Configuration
public class StepConfig {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	@Qualifier("transactionManagerApp")
	private PlatformTransactionManager transactionManager;

	@Bean
	Step step(ItemReader<Pessoa> reader, ItemWriter<Pessoa> writer) {
		return new StepBuilder("step", jobRepository)
				.<Pessoa, Pessoa>chunk(200, transactionManager)
				.reader(reader)
				.writer(writer)
				.build();
	}
}
