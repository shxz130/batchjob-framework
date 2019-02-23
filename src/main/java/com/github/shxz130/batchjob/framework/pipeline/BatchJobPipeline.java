package com.github.shxz130.batchjob.framework.pipeline;

import com.github.shxz130.batchjob.framework.JobEvent;
import com.github.shxz130.batchjob.framework.job.AbstractBatchJob;

import java.util.ArrayList;

/**
 * Created by jetty on 2019/1/24.
 */
public class BatchJobPipeline implements Pipeline{

    private AbstractBatchJob first;

    private ArrayList<AbstractBatchJob> batchJobList=new ArrayList<AbstractBatchJob>();

    public AbstractBatchJob getFirstJob() {
        return batchJobList.get(0);
    }

    public void addJob(AbstractBatchJob batchJob) {
        batchJobList.add(batchJob);
        if(first==null){
            first=batchJob;
        }else{
            for( AbstractBatchJob temp=first; temp!=null;temp=first.getNext()){
                if(temp.getNext()==null){
                    temp.setNext(batchJob);
                    break;
                }
            }
        }
    }

    public AbstractBatchJob getJobByIndex(int i) {
        return batchJobList.get(i);
    }

    public void exec(JobEvent jobEvent) {
        first.handle(jobEvent);
    }

    public void execByIndex(JobEvent jobEvent,int index) {
        batchJobList.get(index).handle(jobEvent);
    }


}

