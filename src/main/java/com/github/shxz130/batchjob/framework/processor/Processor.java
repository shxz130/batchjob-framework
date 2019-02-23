package com.github.shxz130.batchjob.framework.processor;

import com.github.shxz130.batchjob.framework.JobContext;

import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
public interface Processor<T> {
    public void process(JobContext batchContext,List<T> list);

}