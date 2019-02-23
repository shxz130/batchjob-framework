package com.github.shxz130.batchjob.framework.reader;

/**
 * Created by jetty on 2019/1/24.
 */
public abstract class AbstractReader<T> implements Reader{

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
