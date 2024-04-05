package com.xlf.utils;


import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.List;

/**
 * Author: xlf
 * JSON生成实体类
 */
public class GenerateObject {


    /**
     * 读JSON文件转实体类
     * @param url
     * @param <T>
     * @return
     */
    public static <T> T readJSONToObject(String url,Class<T> tClass) {
        String text = read(url);

        T t = JSON.parseObject(text, tClass);

        return t;
    }


    /**
     * 读JSON文件转实体类List,请保证文件是一个JSON格式的字符串列表。
     * @param url
     * @param <T>
     * @return
     */
    public static <T> List<T> readJSONToObjectList(String url,Class<T> tClass){
        //拿到JSON数据
        String text = read(url);

        //转实体类列表
        List<T> list = JSON.parseArray(text, tClass);

        return list;
    }

    /**
     * 读取文件成字符串
     * @param url
     * @return
     */
    public static String read(String url)  {

        StringBuffer res = null;
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {

            res = new StringBuffer();

            int str;
            while ((str = br.read()) != -1) {
                res.append((char) str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res.toString();
    }


    /**
     * 把字符串写到文件里
     * @param text
     * @param url
     */
    public static void write(String text,String url){

        try (BufferedWriter bwr = new BufferedWriter(new FileWriter(url))) {

            bwr.write(text);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
