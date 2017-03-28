/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 信息提示面板
 * @author sicmatr1x
 */
public class HintPane extends Application {
    
    //--------------------------------------------------------------------------
    public HintPane(String title, String hintText, double Width){
        this.title = title;
        this.hint = new Text(hintText);
        this.hint.setWrappingWidth(Width);
    }
    //--------------------------------------------------------------------------
    private final Stage stage = new Stage();
    private final StackPane mainPane = new StackPane();
    private String title;
    /**
     * 文字提示信息
     */
    private Text hint;
    //--------------------------------------------------------------------------
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainPane.getChildren().add(this.hint);
        
        Scene scene = new Scene(mainPane);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false); // 不允许用户调整窗口大小
        stage.show();
    }

}
