package com.github.shxz130.batchjob.demo;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.processor.Processor;
import com.github.shxz130.batchjob.framework.reader.Reader;
import com.github.shxz130.batchjob.framework.step.AbstractJobStep;
import com.github.shxz130.batchjob.framework.writer.Writer;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jetty on 2019/5/17.
 */
@Slf4j
public class DemoWriteDBStep extends AbstractJobStep {


    @Override
    protected void beforeHandle(JobContext jobContext) {
        super.beforeHandle(jobContext);
        log.info("这里记录file防重信息，防止文件重复处理");
        log.info("这里记录file信息，比如说落文件批次表");
    }

    @Override
    protected void afterHandle( JobContext jobContext) {
        log.info("这里更新文件处理状态，这里更新文件批次状态为成功");
        super.afterHandle(jobContext);
    }

    @Override
    protected void dealException(JobContext jobContext,RuntimeException e){
        log.info("这里处理异常，这里更新文件批次状态为失败",e);
    }
}
