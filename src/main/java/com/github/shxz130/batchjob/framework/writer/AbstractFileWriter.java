package com.github.shxz130.batchjob.framework.writer;

import com.github.shxz130.batchjob.framework.JobContext;
import com.github.shxz130.batchjob.framework.JobContextConstants;
import com.github.shxz130.batchjob.framework.utils.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
public abstract class AbstractFileWriter<T> implements Writer<T>{

    public void write(JobContext batchContext, List<T> list) {
        writeFileConfig(batchContext, list);
        //DB读，判断是否第一次读需要判断是否第一页
        Integer dbReaderCurrentPage=(Integer)batchContext.getData(JobContextConstants.DB_READER_CURRENT_PAGE);
        //文件读 需要判断是否是第0行读
        Integer fileReaderBeginIndex=(Integer)batchContext.getData(JobContextConstants.FILE_READER_BEGIN_INDEX);
        //编码方式
        String chaset=(String)batchContext.getData(JobContextConstants.WRITE_FILE_CHASET);
        //文件路径
        String absolutePath=(String)batchContext.getData(JobContextConstants.WRITE_FILE_ABSOLUTE_PATH);
        //临时文件名
        String tempFileName=(String)batchContext.getData(JobContextConstants.WRITE_FILE_TEMP_NAME);

        StringBuffer dataStringBuffer=new StringBuffer();
        if(list==null||list.size()==0){//如果记录数为null，则直接返回0
            return;
        }
        for(int i=0;i <list.size(); i++){
            //如果是第一次写，且写第一行，不换行，否则需要加换行
            if(i==0&&isFirstWrite(dbReaderCurrentPage,fileReaderBeginIndex)){
                dataStringBuffer.append(convertBoToLineString(list.get(i)));
            }else{
                dataStringBuffer.append(System.getProperty("line.separator")+convertBoToLineString(list.get(i)));
            }
        }
        try{
            //如果第一次写，需要写头文件
            if(isFirstWrite(dbReaderCurrentPage,fileReaderBeginIndex)){
                //写文件头
                String title=writeFileTitle(batchContext);
                if(StringUtils.isNotBlank(title)){
                    //如果头不为null，则需要先写头文件，再写文件内容。
                    FileUtils.writeFile(absolutePath, tempFileName, title.toString(), chaset, false);
                    FileUtils.writeFile(absolutePath,tempFileName,dataStringBuffer.toString(),chaset,true);
                }else{
                    //没有头文件，则字节写新文件
                    FileUtils.writeFile(absolutePath, tempFileName, dataStringBuffer.toString(), chaset, false);
                }
            }else{
                //非第一次，则追加文件
                FileUtils.writeFile(absolutePath, tempFileName, dataStringBuffer.toString(), chaset, true);
            }
        }catch (Exception e){
            throw new RuntimeException("文件写失败",e);
        }
    }

    public void reName(JobContext jobContext){
        String absolutePath=(String)jobContext.getData(JobContextConstants.WRITE_FILE_ABSOLUTE_PATH);
        String tempFileName=(String)jobContext.getData(JobContextConstants.WRITE_FILE_TEMP_NAME);
        String realFileName=(String)jobContext.getData(JobContextConstants.WRITE_FILE_REAL_NAME);
        File tempFile=null;
        File realFile=null;
        try{
            tempFile=new File(String.format("%s%s%s",absolutePath,File.separator,tempFileName));
            realFile=new File(String.format("%s%s%s",absolutePath,File.separator,realFileName));
        }catch (Exception e){
            throw new RuntimeException("文件拷贝出错",e);
        }
        if(!tempFile.renameTo(realFile)){
            throw new RuntimeException("文件改名失败");
        }

    }

    /**
     * 写文件头
     *
     * @param jobContext
     * @return
     */
    protected abstract String writeFileTitle(JobContext jobContext);

    private boolean isFirstWrite(Integer dbReaderCurrentPage,Integer fileReaderBeginIndex){
        if(dbReaderCurrentPage!=null&& dbReaderCurrentPage.intValue()==1){
            return true;
        }
        if(fileReaderBeginIndex!=null && fileReaderBeginIndex.intValue()==0){
            return true;
        }
        return false;
    }

    /**
     * 将业务对象转换为写文件的数据
     *
     * @param t
     * @return
     */
    protected abstract String convertBoToLineString(T t);

    /**
     * 写文件需要的配置信息
     *
     * @param batchContext
     * @param list
     */
    protected abstract void writeFileConfig(JobContext batchContext, List<T> dataList);

}
