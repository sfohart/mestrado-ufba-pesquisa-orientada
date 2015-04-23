package br.ufba.dcc.mestrado.computacao.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class MultiCriteriaRecommenderJob implements Job {

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public boolean isRestartable() {
		return false;
	}

	@Override
	public void execute(JobExecution execution) {
		// TODO Auto-generated method stub
	}

	@Override
	public JobParametersIncrementer getJobParametersIncrementer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobParametersValidator getJobParametersValidator() {
		// TODO Auto-generated method stub
		return null;
	}

}
