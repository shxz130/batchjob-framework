package com.github.shxz130.batchjob.framework.reader;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobContextConstants;
import com.github.shxz130.batchjob.framework.utils.FileReaderManager;
import com.github.shxz130.batchjob.framework.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
@Slf4j
public abstract class AbstractFileReader<T> extends AbstractReader{


    public List<T> read(JobContext jobContext, boolean flag, int currentPage, int pageSize) {
        confirmFileNameAndChaset(jobContext);
        jobContext.setData(JobContextConstants.IS_LAST_READ, false);
        String fileName=(String)jobContext.getData(JobContextConstants.FILE_READER_ABSOLUTE_FILE_PATH);
        String chaset=(String)jobContext.getData(JobContextConstants.READ_FILE_CHASET);

        int startNumber=getPreviousEndIndex(jobContext);
        if(currentPage==1){
            if(!new File(fileName).exists()){
                throw new RuntimeException(String.format("file not found,fileName=[%s]",fileName));
            }
            log.info("第一次读文件，打开文件流");
            FileReaderManager.getReaderByName(fileName, chaset);
            jobContext.setData(JobContextConstants.IS_FIRST_READ, true);
            jobContext.setData(JobContextConstants.READER_TYPE,JobContextConstants.READER_TYPE_FILE);
            int fileLineTotalCount= FileUtils.getFileLineNumberCount(fileName);
            jobContext.setData(JobContextConstants.FILE_READER_LINE_TOTAL_COUNT,fileLineTotalCount);
            int dealNumber=querySkipDataNumber(jobContext);
            if(0 == dealNumber){
                int titleLength=readFileTitle(jobContext,startNumber);
                jobContext.setData(JobContextConstants.FILE_READER_FILE_TITLE_COUNT,titleLength);
                startNumber=titleLength;
            }else{
                startNumber=dealNumber;
                FileReaderManager.read(dealNumber);//跳过断点续传逻辑的数据
            }
        }else{
            jobContext.setData(JobContextConstants.IS_FIRST_READ,false);
        }
        jobContext.setData(JobContextConstants.FILE_READER_BEGIN_INDEX,startNumber);
        log.info("读文件开始，从第{}行读",startNumber);
        List<String> fileRecordList= FileReaderManager.read(pageSize);
        jobContext.setData(JobContextConstants.FILE_READER_END_INDEX,startNumber+fileRecordList.size());
        if(fileRecordList.size()!=pageSize){
            log.info("最后一次读文件，关闭文件流");
            FileReaderManager.close();
            jobContext.setData(JobContextConstants.IS_LAST_READ, true);
        }
        log.info("读文件结束，读{}-{}行数据",startNumber,startNumber+fileRecordList.size());
        return convertLineStringToBO(fileRecordList);
    }

    public abstract int getFileTitleLength(JobContext jobContext);

    public abstract int querySkipDataNumber(JobContext jobContext);

    public abstract List<T> convertLineStringToBO(List<String> lineData);

    private int getPreviousEndIndex(JobContext jobContext){
        return (Integer)jobContext.getData(JobContextConstants.FILE_READER_END_INDEX)==null?0:(Integer)(jobContext.getData(JobContextConstants.FILE_READER_END_INDEX));
    }

    protected abstract void confirmFileNameAndChaset(JobContext jobContext);

    private int readFileTitle(JobContext jobContext,int startNumber){
        log.info("读文件头开始，从第{}行读",startNumber);
        int titleLength=getFileTitleLength(jobContext);
        List<String> titleStringList= FileReaderManager.read(titleLength);
        jobContext.setData(JobContextConstants.FILE_READER_FILE_TITLE,titleStringList);
        log.info("读文件头结束，总计读{}行头信息",titleStringList.size());
        return titleStringList.size();
    }
}