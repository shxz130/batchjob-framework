package com.github.shxz130.batchjob.framework.pipeline;

import com.github.shxz130.batchjob.framework.JobEvent;
import com.github.shxz130.batchjob.framework.job.AbstractBatchJob;

/**
 * Created by jetty on 2019/1/24.
 */
public interface Pipeline<T extends JobEvent>{

    public AbstractBatchJob getFirstJob();

    public void addJob(AbstractBatchJob batchJob);

    public AbstractBatchJob getJobByIndex(int i);

    public void exec(T t);

    public void execByIndex(T t,int i);


}
