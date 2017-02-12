package com.guo.base.task;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class BaseTask<K> implements Job {
    private static Logger logger = Logger.getLogger(BaseTask.class);

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // logger.info("###Start [process]..." + this.getClass().getName() +
        // "####");
        try {
            this.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        logger.info("###Start Job [initialization]..." + this.getClass().getName() + "###");
        this.doStart();
    }

    public abstract ExecutorService getPool() throws Exception;

    public abstract List<K> getTaskList() throws Exception;

    public abstract void doStart();

    public abstract Thread getThread(K task);

    public void process() throws Exception {
        ExecutorService pool = this.getPool();
        List<K> list = this.getTaskList();

        for (K task : list) {
            Thread thread = this.getThread(task);
            try {

                pool.execute(thread);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
