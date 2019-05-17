package com.github.shxz130.batchjob.framework.step;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.processor.Processor;
import com.github.shxz130.batchjob.framework.reader.Reader;
import com.github.shxz130.batchjob.framework.utils.FileReaderManager;
import com.github.shxz130.batchjob.framework.writer.AbstractFileWriter;
import com.github.shxz130.batchjob.framework.writer.Writer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
@Slf4j
@Setter
public abstract class AbstractJobStep<T> implements JobStep {

    //读取数据
    private Reader<T> reader;
    //处理数据
    private Processor<T> processor;
    //写入数据
    private Writer<T> writer;
    //分页时每页条数，默认1000
    private int pageSize = 1000;
    //是否分页，默认false
    private boolean flag = true;


    public void doStep(JobContext jobContext) {
        try{
            beforeHandle(jobContext);
            handle(jobContext);
            afterHandle(jobContext);
        }catch (RuntimeException e){
            dealException(jobContext,e);
        }

    }

    protected void dealException(JobContext jobContext,RuntimeException e){
        throw e;
    }

    protected void handle( JobContext jobContext) {
        List<T> list = null;
        int currentPage = 1;
        long startTime=System.currentTimeMillis();
        log.info("文件处理step开始");
        do {
            list = reader.read(jobContext, flag, currentPage, pageSize);
            if(list==null){
                list= Collections.emptyList();
            }
            //处理相应数据
            processor.process(jobContext, list);
            //写文件
            writer.write(jobContext, list);

            currentPage++;
            //分页且能查到数据且查到数据等于每页条数时，继续处理
        } while (flag && list != null && list.size() == pageSize);
        if(writer instanceof AbstractFileWriter){
            FileReaderManager.close();//关闭文件流，方式reader里面关闭不掉，正常来讲，reader里面肯定能够关闭掉
            log.info("文件处理step写文件改名开始");
            ((AbstractFileWriter)writer).reName(jobContext);
            log.info("文件处理step写文件改名结束");
        }
        log.info("文件处理step结束，耗时{}ms",System.currentTimeMillis()-startTime);
    }

    protected void beforeHandle(JobContext jobContext){}


    protected void afterHandle(JobContext jobContext){}

}
