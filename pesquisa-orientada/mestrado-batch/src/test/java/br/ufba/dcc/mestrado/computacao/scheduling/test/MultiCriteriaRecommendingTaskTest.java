package br.ufba.dcc.mestrado.computacao.scheduling.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.ufba.dcc.mestrado.computacao.spring.BatchAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BatchAppConfig.class)
public class MultiCriteriaRecommendingTaskTest {
	
	@Autowired
	@Qualifier(value = "multiCriteriaRecommendingJob")
	private Job job;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Test
	public void testMultiCriteriaRecommendingTask() throws Exception {
		JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
		
		jobLauncherTestUtils.setJob(job);
		jobLauncherTestUtils.setJobLauncher(jobLauncher);
		jobLauncherTestUtils.setJobRepository(jobRepository);
		
		ExitStatus jobExecution = jobLauncherTestUtils.launchJob().getExitStatus();
		
		Assert.assertEquals("COMPLETED", jobExecution.getExitCode());
		
	}

}
