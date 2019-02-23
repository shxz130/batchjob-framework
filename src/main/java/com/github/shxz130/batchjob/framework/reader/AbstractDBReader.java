package com.github.shxz130.batchjob.framework.reader;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobContextConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
@Slf4j
public abstract class AbstractDBReader<T> extends AbstractReader {

    public List<T> read(JobContext jobContext, boolean flag, int currentPage, int pageSize) {
        log.info("Reader读数据开始，读{}页",currentPage);
        jobContext.setData(JobContextConstants.IS_LAST_READ, false);
        jobContext.setData(JobContextConstants.DB_READER_CURRENT_PAGE,currentPage);
        if(currentPage==1){
            jobContext.setData(JobContextConstants.IS_FIRST_READ, true);
            jobContext.setData(JobContextConstants.READER_TYPE,JobContextConstants.READER_TYPE_DB);
        }else{
            jobContext.setData(JobContextConstants.IS_FIRST_READ, false);
        }
        List<T> result=queryDataFromDBByPage(jobContext, currentPage, pageSize);
        if(result==null||result.size()==0){
            result= Collections.emptyList();
        }
        if(result.size()!=pageSize){
            jobContext.setData(JobContextConstants.IS_LAST_READ, true);
        }
        log.info("Reader读数据结束，读{}页，该页读到数据{}行",currentPage,result.size());
        return result;
    }


    protected abstract List<T> queryDataFromDBByPage(JobContext jobContext, int currentPage, int pageSize);
}
