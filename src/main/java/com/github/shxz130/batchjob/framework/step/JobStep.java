package com.github.shxz130.batchjob.framework.step;

import com.github.shxz130.batchjob.framework.JobContext;

/**
 * Created by jetty on 2019/1/24.
 */
public interface JobStep<T> {

    public void doStep(JobContext jobContext);
}
