/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 用于存储设置的类
 * @author sicmatr1x
 */
public class Setting {
    /**
     * 用于存储日志dir的文件夹对象
     */
    public static String _DIARY_SAVE_FOLDER = "D:\\workspace";
    //--------------------------------------------------------------------------
    /**
     * 参数对照表
     */
    private static String[][] ArgsTable = {
        {_DIARY_SAVE_FOLDER, null}
    };
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    /**
     * 参数表同步到字段
     */
    public static void InitSetting(){
        Setting._DIARY_SAVE_FOLDER = Setting.ArgsTable[0][1];
    }
    
    /**
     * 字段同步到参数表
     */
    public static void InitArgsTable(){
        Setting.ArgsTable[0][1] = Setting._DIARY_SAVE_FOLDER;
    }
    
    /**
     * 从文件中加载参数
     * @return 加载成功返回true
     */
    public static boolean ReadArgs(){
        //System.out.println(System.getProperty("user.dir"));
        String currentPath = System.getProperty("user.dir");
        String SettingFileName = currentPath + "\\Setting.txt";
        Scanner input = new Scanner(SettingFileName);
        ArrayList<String> workspace = new ArrayList<>();
        String temp = null;
        while((temp = input.nextLine()) != null){
            workspace.add(temp);
        }
        // 将读取到的参数字符串转换成参数表
        for(int i = 0; i < workspace.size(); i++){
            String[] t = workspace.get(0).split("=");
            for(int j = 0; j < Setting.ArgsTable.length; j++){
                if(Setting.ArgsTable[j][0].equals(t[0])){
                    Setting.ArgsTable[j][1] = t[1];
                }
            }
        }
        return true;
    }
    
    /**
     * 保存设置文件
     */
    public static void WriteArgs(){
        String currentPath = System.getProperty("user.dir");
        String SettingFilePath = currentPath + "\\Setting.txt";
        File SettingFile = new File(SettingFilePath);
        // 将字段同步到参数表
        Setting.InitArgsTable();
        // 将参数表转换成参数字符串
        ArrayList<String> workspace = new ArrayList<>();
        for(int i = 0; i < Setting.ArgsTable.length; i++){
            String t = Setting.ArgsTable[i][0] + Setting.ArgsTable[i][1];
            workspace.add(t);
        }
        try {
            // 保存
            Setting.WriteSettingFile(SettingFile, workspace);
        } catch (IOException ex) {
            System.out.println("WriteArgs():保存设置文件失败" + ex);
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 向文件中写入参数字符串
     * @param Setting 文件
     * @param SettingArgs 参数字符串ArrayList
     * @throws IOException 
     */
    public static void WriteSettingFile(File Setting, ArrayList<String> SettingArgs) throws IOException {

        try (FileOutputStream fout = new FileOutputStream(Setting);
                OutputStreamWriter writer = new OutputStreamWriter(fout, "UTF-8");
                BufferedWriter buf = new BufferedWriter(writer);) {

            for (int i = 0; i < SettingArgs.size(); i++) {
                String temp = SettingArgs.get(0);
                buf.write(temp, 0, temp.length());
                if (i < SettingArgs.size() - 1) {
                    buf.newLine();
                }
            }
        }
    }
    
}
