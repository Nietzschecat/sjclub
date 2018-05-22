package com.huitu.sjclub.util;

/**
 * Created by cys on 2017/9/13.
 */

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;


/**
 * @Desc：word操作工具类
 * @Author：cys
 * @Date：2017-9-13
 */
public class WordUtil {

    /**
     * @Desc：生成word文件
     * @Author：cys
     * @Date：2017-9-13
     * @param dataMap word中需要展示的动态数据，用map集合来保存
     * @param templateName word模板名称，例如：test.ftl
     * @param sjclubPath 文件生成的目标路径，例如：D:/wordFile/
     * @param sjclubName 生成的文件名称，例如：test.doc
     */
    @SuppressWarnings("unchecked")
    public static void createWord(Map dataMap, String templateName, File sjclub, String sjclubPath, String sjclubName){
        try {
            //创建配置实例
            Configuration configuration = new Configuration();

            //设置编码
            configuration.setDefaultEncoding("UTF-8");

            //ftl模板文件统一放至 com.lun.template 包下面
            //configuration.setClassForTemplateLoading(WordUtil.class,"template");
            configuration.setDirectoryForTemplateLoading(sjclub);
            //获取模板
            Template template = configuration.getTemplate(templateName,"UTF-8");

            //输出文件
            File outFile = new File(sjclubPath+ File.separator+sjclubName);

            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }

            //将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));


            //生成文件
            template.process(dataMap, out);

            //关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
