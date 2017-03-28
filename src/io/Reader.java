/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import info.Diary;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author sicmatr1x
 */
public class Reader {
    
    /**
     * 从txt文件中读取设置信息并以行为单位存储到ArrayList<Srring>中
     *
     * @param txt 文件对象
     * @return ArrayList<Srring>
     * @throws FileNotFoundException 未找到文件
     * @throws UnsupportedEncodingException 无法识别的编码
     * @throws IOException 无法读取文件
     */
    public ArrayList<String> readTxt(File txt)
            throws FileNotFoundException, UnsupportedEncodingException, IOException {

        String temp = null;
        ArrayList<String> workSpace = new ArrayList<>();

        try (FileInputStream fin = new FileInputStream(txt); // 
                InputStreamReader reader = new InputStreamReader(fin, "UTF-8");
                BufferedReader buf = new BufferedReader(reader);) {
            while ((temp = buf.readLine()) != null) { // 读入一个行
                //System.out.println("temp=" + temp);
                workSpace.add(temp);
            }
        }

        return workSpace;
    }

    /**
     * 从dir文件读取日志对象
     * @param dir dir文件
     * @return 日志对象Diary
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Diary readFile(File dir)
            throws IOException, ClassNotFoundException {
        Diary tar = null;
        System.out.println("readFile " + dir.getPath());
        try (
                FileInputStream inStream = new FileInputStream(dir);
                ObjectInputStream input = new ObjectInputStream(inStream)
                ) {
                    tar = (Diary) (input.readObject()); // 从文件读取对象
                }
                return tar;
    }
}
