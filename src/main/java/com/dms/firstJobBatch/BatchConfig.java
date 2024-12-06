package com.dms.firstJobBatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

public class BatchConfig {

	@Bean
	Job job(JobRepository jobRepository, Step step) {
		return new JobBuilder("job", jobRepository)
				.start(step)
				.build();
	}
	
	@Bean
	Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step", jobRepository)
				.tasklet(imprimeTasklet(null), transactionManager)
				.build();
	}

	@Bean
	@StepScope
	protected Tasklet imprimeTasklet(@Value("#{jobParameters['name']}") String name) {
		return (StepContribution contribution, ChunkContext chunkContext) -> {
			System.out.println(String.format("Olá, %s!", name));
			return RepeatStatus.FINISHED;
		};
	}
}
