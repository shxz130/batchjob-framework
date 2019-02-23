package com.github.shxz130.batchjob.framework.writer;

import com.github.shxz130.batchjob.framework.JobContext;

import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
public abstract class AbstractDBWriter<T> implements Writer<T>{

    public void write(JobContext batchContext, List<T> list) {
        writeToDB(list);
    }

    public abstract void writeToDB(List<T> list);
}
