package com.github.shxz130.batchjob.framework.reader;

import com.github.shxz130.batchjob.framework.JobContext;

import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
public interface Reader<T> {

    public List<T> read(JobContext jobContext, boolean flag, int currentPage, int pageSize);


}
