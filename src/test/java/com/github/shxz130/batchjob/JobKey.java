package com.github.shxz130.batchjob;

/**
 * Created by jetty on 2019/5/17.
 */
public enum JobKey {

    FYRS_PURCHASE_CONFIRM_FILE("FYRS_PURCHASE_CONFIRM_FILE","富盈人生申购确认文件回盘处理"),
    FYRS_PURCHASE_CONFIRM_FILE_2("FYRS_PURCHASE_CONFIRM_FILE2","富盈人生申购确认文件回盘处理2"),
            ;

    private String code;
    private String message;

    private JobKey(String code,String message){
        this.code=code;
        this.message=message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

