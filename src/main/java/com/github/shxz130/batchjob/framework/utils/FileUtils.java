package com.github.shxz130.batchjob.framework.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jetty on 2019/1/24.
 */
public class FileUtils {


    /**
     * 写文件
     *
     * @param remoteFilePath 文件路径 不包含文件名
     * @param fileName  文件名
     * @param data  数据
     * @param charset   编码方式
     * @param isAppend  是否追加到文件，如果为false，则创建新文件，如果为true，则代表在文件中添加文件
     * @throws IOException
     */
    public static void writeFile(String remoteFilePath, String fileName, String data, String charset,
                                 boolean isAppend) throws IOException {

        File file = new File(remoteFilePath);
        if (!file.exists() && !file.isDirectory()) {// 目录不存在，重新创建
            file.mkdirs();
        }
        String fullPath = remoteFilePath + File.separator + fileName;
        File remoteFile = new File(fullPath);
        // 如果文件已存在,删除对应文件记录
        if (isAppend) {
            appendFile(remoteFile, data, charset);
        } else {
            writeNewFile(remoteFile, data, charset);
        }
    }

    public static void writeNewFile(File file, String data, String charset) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        appendFile(file, data, charset);


    }


    public static void appendFile(File file, String data, String charset) throws IOException {
        FileOutputStream fileOutputStream=null;
        OutputStreamWriter outputStreamWriter=null;
        BufferedWriter writer = null;
        try {
            fileOutputStream=new FileOutputStream(file, true);
            outputStreamWriter=new OutputStreamWriter(fileOutputStream,charset);
            writer =new BufferedWriter(outputStreamWriter);
            writer.write(data);
        } catch (IOException e) {
            throw new IOException("追加文件失败", e);
        }finally {
            if (writer!=null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
            if (outputStreamWriter!=null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e1) {
                }
            }
            if (fileOutputStream!=null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    public static Integer getFileLineNumberCount(String fileName) {
        FileReader fileReader=null;
        LineNumberReader reader=null;
        Integer count = 0;
        try {
            fileReader =new FileReader(new File(fileName));
            reader = new LineNumberReader(fileReader);
            while (reader.readLine() != null) {
                count++;
            }
        } catch (Exception e){
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }catch (Exception e){
            }
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            }catch (Exception e){
            }
        }
        return count;
    }


    public static String getFileName(String filePath, String fileName) {
        return String.format("%s%s%s", filePath, File.separator, fileName);
    }

    public static void createDirectory(String directory) throws Exception {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.isDirectory()) {
            throw new Exception("文件路径出错,不是文件路径");
        }
    }

    public static List<String> readData(String fileName,String chaset,int startNumber,int pageSize){
        FileInputStream fileInputStream=null;
        InputStreamReader inputStreamReader=null;
        LineNumberReader lineNumberReader=null;
        List<String> lineDataList=new ArrayList<String>();
        int number=pageSize;
        try{
            fileInputStream = new FileInputStream(new File(fileName));
            inputStreamReader=new InputStreamReader(fileInputStream,chaset);
            lineNumberReader = new LineNumberReader(inputStreamReader);
            for(int i=0;i<startNumber;i++){
                lineNumberReader.readLine();
            }
            do{
                String lineData=lineNumberReader.readLine();
                if(lineData==null){
                    return lineDataList;
                }
                lineDataList.add(lineData);
                number--;
            }while (number>0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(lineNumberReader!=null){
                    lineNumberReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                if(inputStreamReader!=null){
                    inputStreamReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();;
            }
            try{
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();;
            }
        }
        return lineDataList;
    }
}