package com.github.shxz130.batchjob.framework;

import com.github.shxz130.batchjob.framework.job.AbstractBatchJob;
import com.github.shxz130.batchjob.framework.pipeline.BatchJobPipeline;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jetty on 2019/5/17.
 */
@Slf4j
public class BatchJobPipelineFactory {

    private static final Map<String,BatchJobPipeline> batchJobPipelineMap=new ConcurrentHashMap<String, BatchJobPipeline>();

    private BatchJobPipelineFactory(){
    }


    /**
     * 获取不同业务类型的作业处理pipeLine
     *
     * @param jobKey
     * @return BatchJobPipeline
     */
    public static BatchJobPipeline findBatchJobPipeline(String jobKey){
        return batchJobPipelineMap.get(jobKey);
    }



    public static void registerBatchJobPipeline(String key, BatchJobPipeline batchJobPipeline){
        log.info("jobPipeline注册工作流[{}]",key);
        batchJobPipelineMap.put(key,batchJobPipeline);
    }


}
