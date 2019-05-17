package com.github.shxz130.batchjob.demo;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobContextConstants;
import com.github.shxz130.batchjob.framework.processor.AbstractProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by jetty on 2019/5/17.
 */

@Slf4j
public class DemoFileReaderDBWriterProcessor extends AbstractProcessor<Demo> {


    @Override
    protected void firstReadSpecialProcess(JobContext batchContext) {
        super.firstReadSpecialProcess(batchContext);
        log.info("开始第一次读逻辑");//处理头部信息
        List<String> titleStringList=(List<String>)batchContext.getData(JobContextConstants.FILE_READER_FILE_TITLE);
        log.info("文件头信息为：{}",titleStringList);
        //对头文件进行验证
        int titleFileLength=100000;//假设这个值为头部信息中的文件行数
        //对文件行数进行比对，判断文件是否完整，假设文件行数头部文件有，为titleFileLength;
        int fileLineCount=(Integer)batchContext.getData(JobContextConstants.FILE_READER_LINE_TOTAL_COUNT);//取文件总条数（包含头部信息）
        //fileLineCount为文件总条数，如果需要检查文件总条数是否一致，则需要
        log.info("开始第一次读逻辑，文件总条数为{}",fileLineCount);//处理头部信息
    }


    @Override
    protected void lastReadSpecialProcess(JobContext batchContext) {
        super.lastReadSpecialProcess(batchContext);
        log.info("最后一次读逻辑{}");
    }


    @Override
    protected void doProcess(JobContext batchContext, List<Demo> list) {
        for(Demo demo:list){
            //验证demo对每一行数据进行验证
            log.info("文件处理{}",demo.getKey());
        }
    }


}
