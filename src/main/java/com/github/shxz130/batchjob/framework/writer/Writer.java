package com.github.shxz130.batchjob.framework.writer;

import com.github.shxz130.batchjob.framework.JobContext;

import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
public interface Writer<T> {

    /**
     * 批次写
     *
     * @param batchContext
     * @param list
     */
    public void write(JobContext batchContext,List<T> list);

}
