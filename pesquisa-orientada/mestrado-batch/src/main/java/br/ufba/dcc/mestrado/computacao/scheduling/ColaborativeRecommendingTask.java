package br.ufba.dcc.mestrado.computacao.scheduling;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ColaborativeRecommendingTask extends AbstractRecommendingTask {
	
	private Logger logger = Logger.getLogger(MultiCriteriaRecommendingTask.class.getName());
	
	@Autowired
	public ColaborativeRecommendingTask(
			JobLauncher jobLauncher, 
			@Qualifier("colaborativeRecommendingJob") Job job) {
		super(jobLauncher, job);
	}
	
	@Scheduled(cron = "${colaborative.recommendation.cronExpression}")
	public void doMultiCriteriaRecommending() throws 
				JobExecutionAlreadyRunningException, 
				JobRestartException, 
				JobInstanceAlreadyCompleteException, 
				JobParametersInvalidException {
				
		JobExecution jobExecution = runJob();
		logger.info(String.format("Colaborative Batch Recommending done with status %s", jobExecution.getExitStatus()));
		
	}

}
