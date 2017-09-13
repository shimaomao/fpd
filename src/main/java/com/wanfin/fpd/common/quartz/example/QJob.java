/**  
 * @Project fpd 
 * @Title QJob.java
 * @Package com.wanfin.fpd.common.quartz.example
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月31日 上午9:35:49 
 * @version V1.0   
 */ 
package com.wanfin.fpd.common.quartz.example;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

/**
 * @Description [[_xxx_]] QJob类
 * @author Chenh
 * @date 2016年5月31日 上午9:35:49 
 */
public class QJob implements Job{  
	@Override
    //该方法实现需要执行的任务
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("Generating report - " + arg0.getJobDetail().getFullName() + ", type =" + arg0.getJobDetail().getJobDataMap().get("type"));
        System.out.println(new Date().toString());
    }
	
    public static void main(String[] args) {
        try {
            // 创建一个Scheduler
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();
            // 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
            //该Job负责定义需要执行任务
            JobDetail jobDetail = new JobDetail("myJob", "myJobGroup", QJob.class);
            jobDetail.getJobDataMap().put("type", "FULL");
            // 创建一个每周触发的Trigger，指明星期几几点几分执行
//            Trigger trigger = TriggerUtils.makeWeeklyTrigger(3, 16, 38);
            Trigger trigger = TriggerUtils.makeSecondlyTrigger(5);
            trigger.setGroup("myTriggerGroup");
            // 从当前时间的下一秒开始执行
            trigger.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
            // 指明trigger的name
            trigger.setName("myTrigger");
            // 用scheduler将JobDetail与Trigger关联在一起，开始调度任务
            sched.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
