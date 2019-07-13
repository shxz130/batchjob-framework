package com.github.shxz130.batchjob.framework.processor;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobContextConstants;

import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
public abstract class AbstractProcessor<T> implements Processor {


    /**
     * 业务逻辑处理
     *
     * @param batchContext
     * @param dataList
     */
    public void process(JobContext batchContext, List dataList) {
        boolean isFirstReadFile=(Boolean)batchContext.getData(JobContextConstants.IS_FIRST_READ);
        if(isFirstReadFile){
            firstReadSpecialProcess(batchContext);
        }
        doProcess(batchContext,dataList);
        if(isLastRead(batchContext)){
            lastReadSpecialProcess(batchContext);
        }
    }

    /**
     * 是否最后一次读
     *
     * @param jobContext
     * @return 最后一次读 返回true，非最后一次读，返回false
     */
    private boolean isLastRead(JobContext jobContext){
        return (Boolean)jobContext.getData(JobContextConstants.IS_LAST_READ);
    }

    /**
     * 第一次读的特殊逻辑处理
     *
     * @param batchContext
     */
    protected void firstReadSpecialProcess(JobContext batchContext){}

    /**
     * 最后一次读的特殊逻辑处理
     *
     * @param batchContext
     */
    protected void lastReadSpecialProcess(JobContext batchContext){}

    /**
     * 业务逻辑处理
     *
     * @param batchContext
     * @param dataList
     */
    protected abstract void doProcess(JobContext batchContext, List<T> dataList);
}


