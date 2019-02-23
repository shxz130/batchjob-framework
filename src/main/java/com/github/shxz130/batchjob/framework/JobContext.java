package com.github.shxz130.batchjob.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * job上下文
 *
 * Created by jetty on 2019/1/24.
 */
public class JobContext {

    private JobEvent jobEvent;

    private Map<String,Object> dataMap=new HashMap<String, Object>(16);


    public JobContext() {
    }

    public JobContext(JobEvent jobEvent, Map<String, Object> dataMap) {
        this.jobEvent = jobEvent;
        this.dataMap = dataMap;
    }

    public void setData(String key,Object data){
        dataMap.put(key,data);
    }
    public Object getData(String key){
        return dataMap.get(key);
    }


    public JobContext(JobEvent jobEvent) {
        this.jobEvent = jobEvent;
    }

    public JobEvent getJobEvent() {
        return jobEvent;
    }

    public void setJobEvent(JobEvent jobEvent) {
        this.jobEvent = jobEvent;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

}