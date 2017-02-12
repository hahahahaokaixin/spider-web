package com.guo.base.timer;

import org.quartz.Job;

import com.guo.base.util.QuartzManager;

public class TaskTimer {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TaskTimer.class);

    public static void addJob(Job job, String jobName, String express) {

        try {
            if (QuartzManager.getTrigger(jobName) == null) {
                if (express == null) {
                    QuartzManager.addJob(jobName, job, "0 0/1 * * * ?");
                } else {
                    QuartzManager.addJob(jobName, job, express);
                }
            } else {
                logger.info("已经存在任务:" + jobName);
            }

        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
