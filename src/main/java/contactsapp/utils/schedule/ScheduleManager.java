package contactsapp.utils.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class ScheduleManager {
    private Scheduler scheduler;

    public void shutDown() throws SchedulerException {
        scheduler.shutdown(true);
    }

    public ScheduleManager() throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(CheckContactsBirthday.class)
                .withIdentity("birthdayTracking")
                .build();

        Trigger mailTrigger =(SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("mailTrigger", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(24)
                        .repeatForever())
                .build();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, mailTrigger);
    }
}
