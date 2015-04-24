package br.ufba.dcc.mestrado.computacao.scheduling;

import org.apache.log4j.Logger;
import org.joda.time.Duration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.batch.core.JobParametersBuilder;

@Component
public class MultiCriteriaRecommendingTask {
	
	private Logger logger = Logger.getLogger(MultiCriteriaRecommendingTask.class.getName());
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job multicriteriaRecommendingJob;
		
	@Scheduled(cron = "${user.recommendation.cronExpression}")
	public void doMultiCriteriaRecommending() throws 
				JobExecutionAlreadyRunningException, 
				JobRestartException, 
				JobInstanceAlreadyCompleteException, 
				JobParametersInvalidException {
				
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("timestamp", System.currentTimeMillis());
		
		JobParameters jobParameters = jobParametersBuilder.toJobParameters();
				
		JobExecution jobExecution = jobLauncher.run(multicriteriaRecommendingJob, jobParameters);
		
		logger.info(String.format("MultiCriteria Batch Recommending done with status %s", jobExecution.getExitStatus()));
		
	}
	
}
