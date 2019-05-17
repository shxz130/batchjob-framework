package com.github.shxz130.batchjob.demo;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobContextConstants;
import com.github.shxz130.batchjob.framework.reader.AbstractDBReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by jetty on 2019/5/17.
 */
public class DemoDBReader extends AbstractDBReader<Demo> {

    @Override
    protected List<Demo> queryDataFromDBByPage(JobContext jobContext, int currentPage, int pageSize) {
        //从数据库分页查询，这里模拟分页查询结果返回,假设返回5000000条
        int page=(Integer)jobContext.getData(JobContextConstants.DB_READER_CURRENT_PAGE);
        if((page-1)*1000>=100000){
            return Collections.emptyList();
        }
        List<Demo> list=new ArrayList<Demo>(1000);
        for(int i=0;i<1000;i++){
            list.add(new Demo(""+((page-1)*1000+i+1)+"", UUID.randomUUID().toString()));
        }
        return list;
    }
}
