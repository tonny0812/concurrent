package com.qiuqiu.learn.logmerge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LogMerge {
    public static String mergeLogFiles(String dir) {
        return null;
    }
    public static List<String> getLogFilePathes(String path){
        // get file list where the path has
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();

        List<String> filePathes = new ArrayList<String>();

        for(int i=0;i<array.length;i++){
            if(array[i].isFile()){
                // only take file name
                System.out.println("^^^^^" + array[i].getName());
                // take file path and name
                System.out.println("#####" + array[i]);
                // take file path and name
                System.out.println("*****" + array[i].getPath());
                if(array[i].getName().endsWith(".log")) {
                    filePathes.add(array[i].getAbsolutePath());
                }
            }else if(array[i].isDirectory()){
                filePathes.addAll(getLogFilePathes(array[i].getPath()));
            }
        }
        return filePathes;
    }

    public static void main(String[] args) {
        LogMerge.getLogFilePathes("");
    }
}
