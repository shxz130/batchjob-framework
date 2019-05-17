package com.github.shxz130.batchjob;

import com.github.shxz130.batchjob.demo.*;
import com.github.shxz130.batchjob.framework.BatchJobPipelineFactory;
import com.github.shxz130.batchjob.framework.job.AbstractBatchJob;
import com.github.shxz130.batchjob.framework.pipeline.BatchJobPipeline;
import com.github.shxz130.batchjob.framework.step.AbstractJobStep;

/**
 * Created by jetty on 2019/5/17.
 */
public class Main2 {

    public static void main(String[] args) {

        DemoFileReader demoFileReader=new DemoFileReader();
        DemoDBWriter demoDBWriter=new DemoDBWriter();
        DemoFileReaderDBWriterProcessor demoFileProcessor=new DemoFileReaderDBWriterProcessor();

        AbstractJobStep jobStep=new DemoWriteDBStep();
        jobStep.setProcessor(demoFileProcessor);
        jobStep.setReader(demoFileReader);
        jobStep.setWriter(demoDBWriter);

        AbstractBatchJob job=new DemoJob();
        job.addJobStep(jobStep);

        BatchJobPipeline batchJobPipeline=new BatchJobPipeline();
        batchJobPipeline.addJob(job);
        BatchJobPipelineFactory.registerBatchJobPipeline(JobKey.FYRS_PURCHASE_CONFIRM_FILE.getCode(), batchJobPipeline);

        BatchJobPipelineFactory.findBatchJobPipeline(JobKey.FYRS_PURCHASE_CONFIRM_FILE.getCode()).exec(new DemoJobEvent());


    }
}
