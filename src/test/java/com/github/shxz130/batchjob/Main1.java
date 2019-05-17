package com.github.shxz130.batchjob;

import com.github.shxz130.batchjob.demo.*;
import com.github.shxz130.batchjob.framework.BatchJobPipelineFactory;
import com.github.shxz130.batchjob.framework.job.AbstractBatchJob;
import com.github.shxz130.batchjob.framework.pipeline.BatchJobPipeline;
import com.github.shxz130.batchjob.framework.step.AbstractJobStep;

/**
 * Created by jetty on 2019/5/17.
 */
public class Main1 {

    public static void main(String[] args) {

        DemoDBReader demoDBReader=new DemoDBReader();
        DemoFileWriter demoFileWriter=new DemoFileWriter();
        DemoDBReaderFileWriterProcessor demoFileProcessor=new DemoDBReaderFileWriterProcessor();

        AbstractJobStep jobStep=new DemoWriteDBStep();
        jobStep.setProcessor(demoFileProcessor);
        jobStep.setReader(demoDBReader);
        jobStep.setWriter(demoFileWriter);

        AbstractBatchJob job=new DemoJob();
        job.addJobStep(jobStep);

        BatchJobPipeline batchJobPipeline=new BatchJobPipeline();
        batchJobPipeline.addJob(job);
        BatchJobPipelineFactory.registerBatchJobPipeline(JobKey.FYRS_PURCHASE_CONFIRM_FILE.getCode(), batchJobPipeline);

        BatchJobPipelineFactory.findBatchJobPipeline(JobKey.FYRS_PURCHASE_CONFIRM_FILE.getCode()).exec(new DemoJobEvent());


    }
}
