package com.creat.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @Author: zhouyang
 * @Date: 2020/4/11
 * @Description: 根据模板（.ftl）文件生成word的工具类
 */
public class WordUtil {

    /**
     * @Description: 获取模板
     * @param pathName 模板所在目录
     * @param name 模板名称
     * @return Template
     */
    private static Template getTemplate(String pathName, String name){
        Configuration configuration = new Configuration();
        //configuration.setDirectoryForTemplateLoading(new File(pathName));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(WordUtil.class, pathName);

        Template tempWord = null;
        try {
            tempWord = configuration.getTemplate(name,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempWord;
    }

    /**
     * @Description: 创建word
     * @param pathName 模板所在目录
     * @param name 模板名称
     * @param outputStream 输出流
     * @param dataMap 数据map
     */
    public static void creatWord(String pathName, String name, OutputStream outputStream, Map dataMap){
        Template tempWord = getTemplate(pathName, name);
        try {
            tempWord.process(dataMap, new BufferedWriter(new OutputStreamWriter(
                    outputStream,"utf-8")));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description: 处理本地图片
     * @param imgFile 本地图片地址
     */
    public static String getImageStr(String imgFile) {
        try {
            InputStream in = null;
            byte[] data = null;
            try {
                in = new FileInputStream(imgFile);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Description: 处理网络图片
     * @param imgFile 网络图片地址
     */
    public static String getImageStrFromHttp(String imgFile) {
        try {
            String downPath = "c:/temp.jpg" ;
            saveToFile(imgFile,downPath);
            String imageStr = getImageStr(downPath);
            File file = new File(downPath);
            if (file.exists()){
                file.delete();
            }
            return imageStr;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Description: 下载网路图片
     * @param destUrl 网络图片地址
     *  @param downPath 下载图片地址
     */
    public static void saveToFile(String destUrl, String downPath) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        try {
            url = new URL(destUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            fos = new FileOutputStream(downPath);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.flush();
        } catch (IOException e) {
        } catch (ClassCastException e) {
        } finally {
            try {
                fos.close();
                bis.close();
                httpUrl.disconnect();
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }
        }
    }
}
