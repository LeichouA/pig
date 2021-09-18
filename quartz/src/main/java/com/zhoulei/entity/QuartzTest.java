//package com.zhoulei.entity;
//
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.Trigger;
//import org.quartz.impl.StdSchedulerFactory;
//
//import static org.quartz.JobBuilder.newJob;
//import static org.quartz.TriggerBuilder.newTrigger;
//
//public class QuartzTest {
//
//
//    public static void main(String[] args) throws SchedulerException {
//
//        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = stdSchedulerFactory.getScheduler();
//        scheduler.start();
//
//        // define the job and tie it to our HelloJob class
//        JobDetail job = newJob(HelloJob.class)
//                .withIdentity("myJob", "group1")
//                .build();
//
//        // Trigger the job to run now, and then every 40 seconds
//        Trigger trigger = newTrigger()
//                .withIdentity("myTrigger", "group1")
//                .startNow()
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(40)
//                        .repeatForever())
//                .build();
//
//        // Tell quartz to schedule the job using our trigger
//        scheduler.scheduleJob(job, trigger);
//    }
//}
