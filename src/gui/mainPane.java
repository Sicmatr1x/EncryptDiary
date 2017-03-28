/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import encrypt.AES;
import info.Diary;
import info.Setting;
import io.Reader;
import io.Writer;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author sicmatr1x
 */
public class mainPane extends Application {

    /**
     * 预加载代码块
     */
    static {
        currentPath = System.getProperty("user.dir");
        //Setting.WriteArgs();
    }
    /**
     * 程序当前路径
     */
    static String currentPath = System.getProperty("user.dir");
    /**
     * 日志文件
     */
    static File DiaryFile;
    /**
     * 被译码的日志对象
     */
    static Diary curDiary;
    /**
     * 日志明文,将显示在日志编辑区
     */
    private static String P;
    /**
     * AES密钥
     */
    private static String Key;
    /**
     * 每打开一个日志文件就会将那个日志文件的文件名更新到这个变量里
     */
    public static String curDiaryFileName;
    /**
     * 文件选择器
     */
    private static FileChooser fileChooser;
    //--------------------------------------------------------------------------
    private static Stage stage = new Stage();
    private static BorderPane baseP = new BorderPane();
    private static TextArea mainArea = new TextArea();
    //--------------------------状态栏------------------------------------------
    /**
     * 日志路径
     */
    private static Label curDiaryPath = new Label();
    private static Label curDiaryCreatDate = new Label();
    private static Label curDiaryLastViewDate = new Label();

    /**
     * 刷新日志日期信息栏
     */
    private static void RefreshCurDiaryInfoBar() {
        if (curDiary == null) {
            mainPane.RefreshStatusBar("刷新日志日期信息栏失败：未读取到日志对象");
        }
        mainPane.curDiaryFileName = mainPane.DiaryFile.getName();
        System.out.println(curDiary);
        System.out.println(curDiary.getCreatDate());
        System.out.println(mainPane.DiaryFile.getName());
        System.out.println(curDiary.getLastViewDate());
        curDiaryCreatDate.setText("创建日期" + curDiary.getCreatDate());
        curDiaryLastViewDate.setText("最后访问日期" + curDiary.getLastViewDate());
        curDiaryPath.setText(mainPane.DiaryFile.getPath());
    }
    /**
     * 运行状态
     */
    private static Label status = new Label("状态栏");

    /**
     * 刷新状态栏
     *
     * @param status 运行状态
     */
    private static void RefreshStatusBar(String status) {
        mainPane.status.setText(status);
    }
    //--------------------------------------------------------------------------

    @Override
    public void start(Stage primaryStage) throws Exception {
        fileChooser = new FileChooser(); // 创建文件选择器对象
        fileChooser.setInitialDirectory(new File(currentPath)); // 设置默认路径

        // 载入面板上部(菜单栏)
        GridPane topPane = new GridPane();
        topPane.setHgap(10);
        topPane.setVgap(5);
        MenuBar menuBar = new MenuBar();
        TextField key = new TextField();
        //PasswordField key = new PasswordField();

        key.setPromptText("输入AES密钥");
        key.setOnAction(e -> {
            mainPane.Key = key.getText();
            key.clear();
            System.out.println("密钥更新完毕");
            mainPane.RefreshStatusBar("密钥更新完毕");
        });
        Button btEnter = new Button("更新密钥");
        btEnter.setOnAction(e -> {
            mainPane.Key = key.getText();
            key.clear();
            System.out.println("密钥更新完毕");
            mainPane.RefreshStatusBar("密钥更新完毕");
        });

        // 文件菜单    
        Menu menuFile = new Menu("文件");
        // 打开
        MenuItem openOnly = new MenuItem("打开");
        openOnly.setOnAction(e -> {
            mainPane.openDiary();
        });
        // 打开并译码
        MenuItem openDiary = new MenuItem("打开并译码");
        openDiary.setOnAction(e -> {
            mainPane.openDiaryAndDecrypt();
        });
        // 保存修改的日志文件
        MenuItem saveDiary = new MenuItem("保存修改的日志文件");
        saveDiary.setOnAction(e -> {
            mainPane.saveDiaryToFile();
        });
        // 创建日志文件
        MenuItem saveDiaryTo = new MenuItem("创建日志文件");
        saveDiaryTo.setOnAction(e -> {
            mainPane.createNewDiaryFile();
        });
        // 创建明文日志文件
        MenuItem SaveP = new MenuItem("创建明文日志文件");
        SaveP.setOnAction(e -> {
            mainPane.createNewPDiaryFile();
        });
        menuFile.getItems().addAll(openOnly, openDiary, saveDiaryTo, saveDiary, SaveP); // 添加全部菜单项目至文件菜单

        // 设置菜单
        Menu menuSetting = new Menu("设置");
        MenuItem setDiaryLibraryFolder = new MenuItem("设置日志库");
        setDiaryLibraryFolder.setOnAction(e -> {
            configureFileChooser(fileChooser);
            File folder = fileChooser.showSaveDialog(stage);
            System.out.println("设置日志库" + folder.getPath());
            Setting._DIARY_SAVE_FOLDER = folder.getPath();
            Setting.WriteArgs();
        });
        // 帮助
        Menu menuHelp = new Menu("帮助");
        MenuItem aboutAES = new MenuItem("关于..");
        aboutAES.setOnAction(e -> {
            HintPane infoAES = new HintPane("AES", "高级加密标准（英语：Advanced Encryption Standard，缩写：AES），在密码学中又称Rijndael加密法。本程序采用对象的序列化，将每一个日志对象保存为一个序列化文件(.dir)，并且将日志内容作为一个字符串使用128位AES进行加密。\n\t作者：Sic酱", 300);
            try {
                infoAES.start(stage);
            } catch (Exception ex) {
                mainPane.RefreshStatusBar("打开帮助面板失败" + ex);
                Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menuHelp.getItems().addAll(aboutAES);

        menuBar.getMenus().add(menuFile);
        menuBar.getMenus().add(menuHelp);

        topPane.add(menuBar, 0, 0);
        topPane.add(key, 1, 0);
        topPane.add(btEnter, 2, 0);
        topPane.add(mainPane.curDiaryCreatDate, 0, 1);
        topPane.add(mainPane.curDiaryLastViewDate, 1, 1);
        mainPane.baseP.setTop(topPane);

        // 载入面板中部(日志编辑部分)
        LoadEditTextArea();

        // 载入面板下部(状态部分)
        GridPane bottomPane = new GridPane();
        bottomPane.setHgap(10);
        bottomPane.setVgap(10);
        bottomPane.add(mainPane.curDiaryPath, 1, 1);
        bottomPane.add(mainPane.status, 1, 2);
        mainPane.baseP.setBottom(bottomPane);

        // 设置场景并展示
        Scene scene = new Scene(baseP, 700, 500);
        scene.setOnKeyPressed(mainKey -> {
            if (mainKey.isControlDown()) {
                System.out.println("ControlDown");
                scene.setOnKeyPressed(assistKey -> {
                    if(assistKey.getCode().equals(KeyCode.S)){
                        System.out.println("S Down");
                        mainPane.saveDiaryToFile();
                    }
                });
            }
        });
        stage.setTitle("EncryptDiary");
        stage.setScene(scene);
        //stage.setResizable(false); // 不允许用户调整窗口大小
        stage.show();
    }

    //----------------------------部分面板载入函数-------------------------------
    /**
     * 载入面板中部(日志编辑部分)
     */
    public static void LoadEditTextArea() {
        mainPane.baseP.setCenter(mainPane.mainArea);
    }

    //---------------------------部分功能函数------------------------------------

    /**
     * 打开日志文件
     */
    private static void openDiary() {
        mainPane.RefreshStatusBar("打开文件");
        DiaryFile = fileChooser.showOpenDialog(stage);
        mainPane.RefreshStatusBar("加载日志信息");
        System.out.println("Loading:" + DiaryFile.getPath());
        try {
            mainPane.RefreshStatusBar("读取日志信息");
            curDiary = Reader.readFile(DiaryFile);
            mainPane.RefreshCurDiaryInfoBar(); // 刷新日志信息
        } catch (IOException ex) {
            mainPane.RefreshStatusBar("打开错误：" + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            mainPane.RefreshStatusBar("找不到对象：" + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        // 3.载入日志内容
        mainPane.mainArea = new TextArea(curDiary.getContent());
        mainPane.RefreshStatusBar("已载入日志密文");
        mainPane.baseP.setCenter(mainPane.mainArea);
    }

    /**
     * 打开日志文件并译码
     */
    private static void openDiaryAndDecrypt() {
        mainPane.RefreshStatusBar("打开文件");
        configureFileChooser(fileChooser);
        DiaryFile = fileChooser.showOpenDialog(stage);
        mainPane.RefreshStatusBar("加载日志信息");
        System.out.println("Loading:" + DiaryFile.getPath());
        try {
            mainPane.RefreshStatusBar("读取日志信息");
            curDiary = Reader.readFile(DiaryFile);
            mainPane.RefreshCurDiaryInfoBar(); // 刷新日志信息
            // 2.解密日志内容
            mainPane.RefreshStatusBar("译码中...");
            //System.out.println("curDiary.getContent():" + curDiary.getContent());
            mainPane.P = AES.aesDecrypt(curDiary.getContent(), mainPane.Key);
            mainPane.RefreshStatusBar("译码完毕");
                //mainPane.P = AES.aesDecrypt(curDiary.getContent(), "123456");
            // 3.载入日志内容
            mainPane.mainArea = new TextArea(P);
            mainPane.baseP.setCenter(mainPane.mainArea);
        } catch (IOException ex) {
            System.out.println("Open Error " + ex);
            mainPane.RefreshStatusBar("打开错误：" + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            mainPane.RefreshStatusBar("找不到对象：" + ex);
            System.out.println("ClassNotFoundException " + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            mainPane.RefreshStatusBar("译码错误：" + ex);
            System.out.println("Menu:openDiary " + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 保存修改的日志文件;
     */
    private static void saveDiaryToFile() {
        System.out.println("Save:" + Setting._DIARY_SAVE_FOLDER);
        mainPane.RefreshStatusBar("保存文件");
        // 1.获取TextArea的文本内容
        String temp = mainPane.mainArea.getText();
        //System.out.println("TextArea=" + temp);
        try {
            // 2.加密日志内容并更新对象
            mainPane.RefreshStatusBar("加密中...");
            String C = AES.aesEncrypt(temp, Key);
            mainPane.curDiary.EditContent(C);
            mainPane.RefreshStatusBar("加密完毕");
        } catch (Exception ex) {
            System.out.println("加密错误");
            mainPane.RefreshStatusBar("加密错误" + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        // 3.保存密文
        try {
            //Writer.CreateDiary(new File(Setting._DIARY_SAVE_FOLDER), curDiary);
            mainPane.RefreshStatusBar("写入文件中");
            Writer.EditDiary(DiaryFile, curDiary);
            mainPane.RefreshStatusBar("文件写入完毕");
        } catch (IOException ex) {
            System.out.println("Save Error:" + ex);
            mainPane.RefreshStatusBar("写入文件失败" + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 创建日志文件
     */
    private static void createNewDiaryFile() {
        configureFileChooser(fileChooser);
        fileChooser.setInitialFileName(".dir");
        File file = fileChooser.showSaveDialog(stage);
        System.out.println("SaveTo:" + file.getPath());
        mainPane.RefreshStatusBar("创建日志文件");
        // 1.获取TextArea的文本内容
        String temp = mainPane.mainArea.getText();
        try {
            // 2.加密日志内容并更新对象
            mainPane.curDiary = new Diary();
            mainPane.RefreshStatusBar("开始加密...");
            String C = AES.aesEncrypt(temp, Key);
            mainPane.curDiary.CreatContent(C);
        } catch (Exception ex) {
            System.out.println("加密错误");
            mainPane.RefreshStatusBar("加密错误" + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.RefreshStatusBar("加密完毕");
        // 3.保存密文
        try {
            mainPane.RefreshStatusBar("写入文件...");
            Writer.EditDiary(file, curDiary);
        } catch (IOException ex) {
            System.out.println("SaveTo Error:" + ex);
            mainPane.RefreshStatusBar("写入文件失败" + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.RefreshStatusBar("写入文件成功" + file.getPath());
    }

    /**
     * 创建明文日志文件
     */
    private static void createNewPDiaryFile() {
        configureFileChooser(fileChooser);
        fileChooser.setInitialFileName(".dir");
        File file = fileChooser.showSaveDialog(stage);
        System.out.println("SaveTo:" + file.getPath());
        mainPane.RefreshStatusBar("创建日志文件");
        // 1.获取TextArea的文本内容
        String temp = mainPane.mainArea.getText();
        mainPane.curDiary = new Diary();
        mainPane.curDiary.CreatContent(temp);
        // 3.保存密文
        try {
            mainPane.RefreshStatusBar("写入文件...");
            Writer.EditDiary(file, curDiary);
        } catch (IOException ex) {
            System.out.println("SaveTo Error:" + ex);
            mainPane.RefreshStatusBar("写入文件失败" + ex);
            Logger.getLogger(mainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.RefreshStatusBar("写入文件成功" + file.getPath());
    }

    //--------------------------------------------------------------------------
    /**
     * 选择文件窗口
     *
     * @param fileChooser
     */
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("选择要打开的文件");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    //--------------------------------------------------------------------------
    public static void main(String[] args) {
        Application.launch(args);
    }
}
