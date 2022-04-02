package pr1;

import java.util.logging.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import pr1.cron.TopicJob;

public class Pr1MainClass {
	
	private static final int TEMPS_INTERVAL_MINUTES = 1;
	private static final Logger LOGGER = Logger.getLogger(Pr1MainClass.class.getName());
	
	public static void main(String[] args) throws SchedulerException {
		
		LOGGER.info("Démarrage du Producer n°1");
		
		JobDetail job = JobBuilder.newJob(TopicJob.class).build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				  .withIdentity("monTrigger", "groupe1")
				  .startNow()
				  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
				    .withIntervalInSeconds(60 * TEMPS_INTERVAL_MINUTES)
				    .repeatForever())
				  .build();
		
		Scheduler s = StdSchedulerFactory.getDefaultScheduler();
		
		s.start();
		s.scheduleJob(job, trigger);
	}
}
