package com.github.shxz130.batchjob.demo;

import com.github.shxz130.batchjob.framework.writer.AbstractDBWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by jetty on 2019/5/17.
 */

@Slf4j
public class DemoDBWriter extends AbstractDBWriter<Demo> {


    @Override
    public void writeToDB(List<Demo> list) {
        for(Demo demo: list){
            log.info("假设文件写入库demo",demo);
            System.out.println("假设文件写入库demo"+demo.getKey()+demo.getValue());
        }
    }
}