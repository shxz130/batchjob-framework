package com.github.shxz130.batchjob.framework.job;

import com.github.shxz130.batchjob.framework.JobEvent;

/**
 * Created by jetty on 2019/1/24.
 */
public interface BatchJob<T extends JobEvent> {

    public void handle(T event);

}
