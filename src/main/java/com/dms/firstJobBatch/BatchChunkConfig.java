package com.dms.firstJobBatch;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchChunkConfig {

	@Bean
	Job job(JobRepository jobRepository, Step step) {
		return new JobBuilder("job", jobRepository).start(step).incrementer(new RunIdIncrementer()).build();
	}

	@Bean
	Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step", jobRepository).<Integer, String>chunk(1, transactionManager)
				.reader(countToTenReader()).processor(evenOrOddProcessor()).writer(printWriter()).build();
	}

	private ItemReader<Integer> countToTenReader() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		return new IteratorItemReader<>(numbers.iterator());
	}

	private String result(Integer item, String is) {
		return String.format("Item %s é %s", item, is);
	}

	private ItemProcessor<Integer, String> evenOrOddProcessor() {
		return new FunctionItemProcessor<Integer, String>(
				item -> item % 2 == 0 ? result(item, "par") : result(item, "impar"));
	}

	private ItemWriter<String> printWriter() {
		return item -> item.forEach(System.out::println);
	}
}
