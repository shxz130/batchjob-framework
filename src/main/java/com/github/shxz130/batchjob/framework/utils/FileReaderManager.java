package com.github.shxz130.batchjob.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
public class FileReaderManager {

    private static ThreadLocal<FileInputStream> fileInputStreamThreadLocal=new ThreadLocal<FileInputStream>();
    private static ThreadLocal<InputStreamReader> inputStreamReaderThreadLocal=new ThreadLocal<InputStreamReader>();
    private static ThreadLocal<LineNumberReader> lineNumberReaderThreadLocal=new ThreadLocal<LineNumberReader>();

    public static LineNumberReader getReaderByName(String fileName,String chaset) {
        LineNumberReader lineNumberReader = lineNumberReaderThreadLocal.get();
        if (lineNumberReader != null) {
            return lineNumberReader;
        } else {
            FileInputStream fileInputStream = null;
            InputStreamReader inputStreamReader = null;
            try {
                fileInputStream = new FileInputStream(new File(fileName));
                inputStreamReader = new InputStreamReader(fileInputStream, chaset);
                lineNumberReader = new LineNumberReader(inputStreamReader);
                fileInputStreamThreadLocal.set(fileInputStream);
                inputStreamReaderThreadLocal.set(inputStreamReader);
                lineNumberReaderThreadLocal.set(lineNumberReader);
            } catch (Exception e) {
            }
        }
        return lineNumberReaderThreadLocal.get();
    }

    public static List<String> read(int number){
        LineNumberReader lineNumberReader= lineNumberReaderThreadLocal.get();
        List<String> lineDataList=new ArrayList<String>();
        do{
            String lineData=null;
            try{
                lineData=lineNumberReader.readLine();
            }catch (Exception e){
            }
            if(lineData==null){
                return lineDataList;
            }
            lineDataList.add(lineData);
            number--;
        }while (number>0);
        return lineDataList;
    }



    public static void close(){
        LineNumberReader lineNumberReader=lineNumberReaderThreadLocal.get();
        InputStreamReader inputStreamReader=inputStreamReaderThreadLocal.get();
        FileInputStream  fileInputStream=  fileInputStreamThreadLocal.get();
        close(lineNumberReader,inputStreamReader,fileInputStream);
        lineNumberReaderThreadLocal.remove();
        inputStreamReaderThreadLocal.remove();
        fileInputStreamThreadLocal.remove();
    }

    public static void close( LineNumberReader lineNumberReader,InputStreamReader inputStreamReader,FileInputStream  fileInputStream){
        if(lineNumberReader!=null) {
            try {
                lineNumberReader.close();
            } catch (Exception e){
            }
        }
        if(inputStreamReader!=null){
            try{
                inputStreamReader.close();
            }catch (Exception e){
            }
        }
        if(fileInputStream!=null) {
            try {
                fileInputStream.close();
            } catch (Exception e) {
            }
        }
    }
}

