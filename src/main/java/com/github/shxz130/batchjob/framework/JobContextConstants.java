package com.github.shxz130.batchjob.framework;

/**
 * Created by jetty on 2019/1/24.
 */
public class JobContextConstants {
    /**
     * 读的当前页数
     */
    public static final String DB_READER_CURRENT_PAGE="DB_READER_CURRENT_PAGE";

    /**
     * 文件读的文件路径
     */
    public static final String FILE_READER_ABSOLUTE_FILE_PATH = "FILE_READER_ABSOLUTE_FILE_PATH";

    /**
     * 文件头信息
     */
    public static final String FILE_READER_FILE_TITLE="FILE_READER_FILE_TITLE";

    /**
     * 文件头信息
     */
    public static final String FILE_READER_FILE_TITLE_COUNT ="FILE_READER_FILE_TITLE_COUNT";
    /**
     * 文件该批次开始读的上标数
     */
    public static final String FILE_READER_BEGIN_INDEX="FILE_READER_BEGIN_INDEX";

    /**
     * 文件该批次开始读的下标数
     */
    public static final String FILE_READER_END_INDEX="FILE_READER_END_INDEX";


    /**
     * 写文件的临时文件路径定义KEY
     */
    public static final String WRITE_FILE_ABSOLUTE_PATH ="WRITE_FILE_ABSOLUTE_PATH";

    /**
     * 写文件的最终绝对文件路径
     */
    public static final String WRITE_FILE_TEMP_NAME ="WRITE_FILE_TEMP_NAME";
    /**
     * 写文件的最终绝对文件路径
     */
    public static final String WRITE_FILE_REAL_NAME ="WRITE_FILE_REAL_NAME";

    /**
     * 读文件的文件格式
     */
    public static final String WRITE_FILE_CHASET="WRITE_FILE_CHASET";
    /**
     * 读文件的文件格式
     */
    public static final String READ_FILE_CHASET ="READ_FILE_CHASET";
    /**
     * 是否第一次读
     */
    public static final String IS_FIRST_READ="IS_FIRST_READ";

    /**
     * 文件总行数
     */
    public static final String FILE_READER_LINE_TOTAL_COUNT="FILE_READER_LINE_TOTAL_COUNT";

    /**
     * READTYPE
     */
    public static final String READER_TYPE="READER_TYPE";
    /**
     * READTYPE
     */
    public static final String READER_TYPE_DB="DB";

    /**
     * READTYPE_FILE
     */
    public static final String READER_TYPE_FILE="FILE";

    /**
     * IS_LAST_READ
     */
    public static final String IS_LAST_READ ="IS_LAST_READ";
}