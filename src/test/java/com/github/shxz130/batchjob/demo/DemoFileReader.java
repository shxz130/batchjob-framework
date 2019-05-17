package com.github.shxz130.batchjob.demo;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobContextConstants;
import com.github.shxz130.batchjob.framework.reader.AbstractFileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jetty on 2019/5/17.
 */
public class DemoFileReader extends AbstractFileReader<Demo> {

    //获取文件头部信息长度
    @Override
    public int getFileTitleLength(JobContext jobContext) {
        return 2;
    }

    @Override
    protected void confirmFileNameAndChaset(JobContext jobContext) {
        jobContext.setData(JobContextConstants.FILE_READER_ABSOLUTE_FILE_PATH, "/Users/jetty/Documents/data/reala.txt");
        jobContext.setData(JobContextConstants.READ_FILE_CHASET, "utf-8");
    }

    //断点续传，如果读文件从第几行跳过开始读，则指定取数规则
    //第几行读包含头部长度，这里需要注意，避免重复获取数据
    @Override
    public int querySkipDataNumber(JobContext jobContext) {
        return 0;
    }

    @Override
    public List<Demo> convertLineStringToBO(List<String> lineData) {
        List<Demo> demoList=new ArrayList<Demo>(1000);
        if(lineData!=null&&lineData.size()!=0){
            for(String data:lineData){
                String[] lineDataArray=data.split("\\|");
                demoList.add(new Demo(lineDataArray[0],lineDataArray[1]));
            }
        }
        return demoList;
    }
}
