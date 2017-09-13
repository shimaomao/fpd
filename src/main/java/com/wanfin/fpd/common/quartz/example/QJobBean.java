package com.wanfin.fpd.common.quartz.example;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QJobBean  extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
			System.err.println("测试Quartz"+new Date());
	}
}
