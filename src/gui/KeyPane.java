/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 密钥输入面板
 * @author sicmatr1x
 */
public class KeyPane extends Application{
    /**
     * AES密钥
     */
    public static String Key;
    //--------------------------------------------------------------------------
    private final Stage stage = new Stage();
    private final GridPane mainPane = new GridPane();
    //--------------------------------------------------------------------------

    @Override
    public void start(Stage primaryStage) throws Exception {
        Text hint = new Text("输入AES密钥");
        TextField key = new TextField();
        key.setOnAction(e -> {
            KeyPane.Key = key.getText();
            this.notifyAll();
            stage.close();
        });
        
        Button btEnter = new Button("确定");
        btEnter.setOnAction(e -> {
            KeyPane.Key = key.getText();
            this.notifyAll();
            stage.close();
        });
        mainPane.add(hint, 1, 1);
        mainPane.add(key, 1, 2);
        mainPane.add(btEnter, 1, 3);
        
        
        
        Scene scene = new Scene(mainPane);
        stage.setTitle("密钥输入");
        stage.setScene(scene);
        stage.setResizable(false); // 不允许用户调整窗口大小
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
