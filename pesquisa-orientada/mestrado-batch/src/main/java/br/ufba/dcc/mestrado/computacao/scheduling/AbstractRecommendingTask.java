package br.ufba.dcc.mestrado.computacao.scheduling;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

public class AbstractRecommendingTask {
	
	private Job job;
	private JobLauncher jobLauncher;

	public AbstractRecommendingTask(JobLauncher jobLauncher, Job job) {
		this.job = job;
		this.jobLauncher = jobLauncher;
	}
	
	protected JobExecution runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobExecution jobExecution = jobLauncher.run(job, generateJobParameters());
		return jobExecution;
	}
	
	protected JobParameters generateJobParameters() {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("timestamp", System.currentTimeMillis());
		jobParametersBuilder.addString("job", job.getName());
		
		JobParameters jobParameters = jobParametersBuilder.toJobParameters();
		return jobParameters;
	}
	
	
}
