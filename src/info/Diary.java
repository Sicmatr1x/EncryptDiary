/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志类
 * @author sicmatr1x
 */
public class Diary implements Serializable{
    private static final long serialVersionUID = 2L;
    /**
     * 创建日期
     */
    private Date CreatDate;
    /**
     * 最后访问日期
     */
    private Date LastViewDate;
    /**
     * 最后修改日期
     */
    private Date LastEditData;
    /**
     * 加密方式
     */
    private String EncryptionMethod;
    /**
     * 字符编码方式
     */
    private String charsetName;
    //--------------------------------------------------------------------------
    /**
     * 日志正文
     */
    private String content;
    
    //--------------------------------------------------------------------------
    public Diary(){
        this.CreatDate = new Date();
    }
    //--------------------------------------------------------------------------
    /**
     * 创建日志
     * @param NewContent
     */
    public void CreatContent(String NewContent){
        this.content = NewContent;
    }
    
    /**
     * 修改日志，此操作会更新最后修改日期
     * @param Content 
     */
    public void EditContent(String Content){
        this.content = Content;
        this.LastViewDate = new Date();
    }
    
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    } 
    //--------------------------------------------------------------------------

    /**
     * @return the CreatDate
     */
    public Date getCreatDate() {
        System.out.println("getCreatDate()" + CreatDate);
        return CreatDate;
    }

    /**
     * @return the LastViewDate
     */
    public Date getLastViewDate() {
        return LastViewDate;
    }

    /**
     * @param LastViewDate the LastViewDate to set
     */
    public void setLastViewDate(Date LastViewDate) {
        this.LastViewDate = LastViewDate;
    }

    /**
     * @return the LastEditData
     */
    public Date getLastEditData() {
        return LastEditData;
    }

    /**
     * @param LastEditData the LastEditData to set
     */
    public void setLastEditData(Date LastEditData) {
        this.LastEditData = LastEditData;
    }

    /**
     * @return the EncryptionMethod
     */
    public String getEncryptionMethod() {
        return EncryptionMethod;
    }

    /**
     * @param EncryptionMethod the EncryptionMethod to set
     */
    public void setEncryptionMethod(String EncryptionMethod) {
        this.EncryptionMethod = EncryptionMethod;
    }

    /**
     * @return the charsetName
     */
    public String getCharsetName() {
        return charsetName;
    }

    /**
     * @param charsetName the charsetName to set
     */
    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }
    
}
