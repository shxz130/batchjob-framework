package com.github.shxz130.batchjob.framework.job;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobEvent;
import com.github.shxz130.batchjob.framework.step.JobStep;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
@Slf4j
@Data
public abstract class AbstractBatchJob<T extends JobEvent> implements BatchJob<T>{

    private AbstractBatchJob next;

    protected List<JobStep> jobStepList=new ArrayList<JobStep>(2);


    protected void handleBefore(JobContext jobContext){
    };

    public void handle(T jobEvent) {
        log.info("[batchJob][EVENT]{} start", jobEvent);
        long start=System.currentTimeMillis();
        JobContext jobContext=new JobContext(jobEvent);
        try{
            handleBefore(jobContext);
            doHandle(jobContext);
            handleAfter(jobContext,jobEvent);
        }catch (Exception e){
            dealException(e);
        }
        log.info("[batchJob][EVENT] end 耗时{}ms", System.currentTimeMillis() - start);
    }

    protected void doHandle(JobContext jobContext){
        for(JobStep jobStep:jobStepList){
            JobContext stepJobContext=new JobContext(jobContext.getJobEvent(),new HashMap<String, Object>(jobContext.getDataMap()));
            jobStep.doStep(stepJobContext);
        }
    }

    protected void handleAfter(JobContext jobContext,JobEvent jobEvent){}

    protected void dealException(Exception e){
        log.error("[batchJob][EVENT] exception:", e);
    }


    public AbstractBatchJob addJobStep(JobStep jobStep){
        jobStepList.add(jobStep);
        return this;
    }

    public AbstractBatchJob getNext() {
        return next;
    }

    public void setNext(AbstractBatchJob next) {
        this.next = next;
    }
}