/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import gui.mainPane;
import info.Diary;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 *
 * @author sicmatr1x
 */
public class Writer {

    /**
     * 以覆盖模式将日志对象写入到dir文件里
     *
     * @param file 代写入文件对象
     * @param EditDiary 代写入日志对象
     * @return 写入后dir文件的大小(字节)
     * @throws IOException
     */
    public static long EditDiary(File file, Diary EditDiary)
            throws IOException {
        System.out.println("EditDiary");
        try (
                ObjectOutputStream output = new ObjectOutputStream(
                        new FileOutputStream(file))) {

                    output.writeObject(EditDiary); // 将对象写入文件

                    File tar = new File(file.toString());
                    if (tar.exists()) {
                        return tar.length();
                    } else {
                        throw new IOException("Write " + file.getPath() + " fail");
                    }

                }
    }

    /**
     * 创建新日志文件,自动按文件名顺序命名新建的文件
     *
     * @param folder 日志文件夹
     * @param diary 新日志对象
     * @return 创建好的对象文件大小(字节)
     * @throws IOException
     */
    public static long CreateDiary(File folder, Diary diary) throws IOException {
        System.out.println("CreateDiary");
        if (folder.isFile()) {
            System.out.println("error:" + folder.getPath() + " is not a folder");
            return -1;
        }
        // 计算文件编号
        File[] workSpace = folder.listFiles();
        int CurrentNum = workSpace.length;
        // 构造文件名
        String fileName = "Diary" + CurrentNum + ".dir";
        File NewFile = new File(folder.getPath() + "\\" + fileName);
        // 写入文件
        try (
                ObjectOutputStream output = new ObjectOutputStream(
                        new FileOutputStream(NewFile))) {

                    output.writeObject(diary); // 将对象写入文件

                    File tar = new File(NewFile.toString());
                    if (tar.exists()) {
                        return tar.length();
                    } else {
                        throw new IOException("Write " + NewFile.getPath() + " fail");
                    }

                }
    }

    /**
     * 判断当前打开的日子文件在日志文件夹中存不存在
     *
     * @param folder 日志文件夹
     * @return 存在true
     */
    public boolean isCurDiaryExixt(File folder) {
        System.out.println("isCurDiaryExixt()");
        File[] FileList = folder.listFiles();
        for (int i = 0; i < FileList.length; i++) {
            if (mainPane.curDiaryFileName.equals(FileList[i])) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
}
