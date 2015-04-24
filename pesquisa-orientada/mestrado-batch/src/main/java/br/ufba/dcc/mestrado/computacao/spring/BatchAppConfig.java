package br.ufba.dcc.mestrado.computacao.spring;

import java.util.logging.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import br.ufba.dcc.mestrado.computacao.batch.exception.MultiCriteriaBatchRecommenderException;
import br.ufba.dcc.mestrado.computacao.batch.item.UserProcessor;
import br.ufba.dcc.mestrado.computacao.batch.item.UserReader;
import br.ufba.dcc.mestrado.computacao.batch.item.UserRecommendationWriter;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

@Configuration
@EnableBatchProcessing
@EnableAsync
@EnableScheduling
@Import({ CoreAppConfig.class, RecommenderAppConfig.class })
@PropertySource(value = {	
		"classpath:batch.properties"
	})
public class BatchAppConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Value("${user.recommendation.commitInterval:5}")
	private Integer commitInterval;
	
	@Autowired
	private UserReader userReader;
	
	@Autowired
	private UserProcessor userProcessor;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private UserRecommendationWriter userRecommendationWriter;
	
	
	
	@Bean
	public TaskScheduler buildTaskScheduler() {
		TaskScheduler taskScheduler = new ConcurrentTaskScheduler();
		
		return taskScheduler;
	}
	
	@Bean
	public JobLauncherTestUtils jobLauncherTestUtils() {
		JobLauncherTestUtils utils = new JobLauncherTestUtils();
		
		utils.setJobLauncher(jobLauncher);
		utils.setJobRepository(jobRepository);
		utils.setJob(userRecommendationJob());
		
		return utils;
	}
	
	@Bean
	public JobParameters buildJobParameters() {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("timestamp", System.currentTimeMillis());
		
		JobParameters jobParameters = jobParametersBuilder.toJobParameters(); 
		
		return jobParameters;
	}
	
	@Bean
	public Job userRecommendationJob() {
		Job job = jobBuilderFactory.get("userRecommendationJob")
				.start(userRecommendationStep(userReader, userProcessor, userRecommendationWriter))
				.build();
								
		return job;
	}
		
	@Bean
	public Step userRecommendationStep(UserReader reader, UserProcessor processor, UserRecommendationWriter writer) {
		Step step = stepBuilderFactory.get("userRecommendationStep")
				.<UserEntity,UserRecommendationEntity>chunk(commitInterval)
				.reader(reader)
				.processor(processor)
					.faultTolerant()
					.skip(MultiCriteriaBatchRecommenderException.class)
					.skipPolicy(skipPolicy())
					.noRetry(MultiCriteriaBatchRecommenderException.class)
				.writer(writer)					
				.build();
		
		
		return step;
	}
	
	@Bean
	public SkipPolicy skipPolicy() {
		return new AlwaysSkipItemSkipPolicy();
	}
	
}
