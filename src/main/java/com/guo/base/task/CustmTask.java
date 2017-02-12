package com.guo.base.task;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class CustmTask implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.doWork();
	}

	@PostConstruct
	public void init() {
		System.out.println("############Cust Task start###############");
		this.doStart();
	}


	public abstract void doStart();

	public abstract void doWork();
}
