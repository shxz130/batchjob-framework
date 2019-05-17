package com.github.shxz130.batchjob.demo;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobContextConstants;
import com.github.shxz130.batchjob.framework.writer.AbstractFileWriter;

import java.util.List;

/**
 * Created by jetty on 2019/5/17.
 */
public class DemoFileWriter extends AbstractFileWriter<Demo> {


    @Override
    protected String writeFileTitle(JobContext jobContext) {
        return "fileTitle"+ System.getProperty("line.separator")+"总条数：100000000"+System.getProperty("line.separator");
    }

    @Override
    protected void writeFileConfig(JobContext batchContext, List<Demo> list) {
        batchContext.setData(JobContextConstants.WRITE_FILE_ABSOLUTE_PATH,"/Users/jetty/Documents/data");
        batchContext.setData(JobContextConstants.WRITE_FILE_TEMP_NAME,"tempa.txt");
        batchContext.setData(JobContextConstants.WRITE_FILE_REAL_NAME,"reala.txt");
        batchContext.setData(JobContextConstants.WRITE_FILE_CHASET,"utf-8");
    }

    @Override
    protected String convertBoToLineString(Demo demo) {
        return demo.getKey()+"|"+demo.getValue();
    }
}
