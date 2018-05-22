package com.huitu.sjclub.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.*;

/**
 * Created by cys on 2017/8/4.
 */
public class XmlHelper {


    public static Map<String, String> readXml(Document document) {
        Element element = document.getRootElement();
        Map<String, String> maps = new HashMap<String, String>();
        List<Element> elements = element.elements();
        for (Element element1 : elements) {
            if (element1.getName().equals("table")) {
                List<Element> list = element1.elements();
                String str = "";
                for (Element sjclubds : list) {
                    if (sjclubds.getName().equals("sjclubd")) {
                        String sjclubd = listNodes(sjclubds, "sjclubd");
                        str += sjclubd + ",";
                    }
                }
                str = str.substring(0, str.length() - 1);
                maps.put(element1.attributeValue("name"), str);
            }
        }
        return maps;
    }

    //读取xml
    public static Map<String, String> readXml(String xmlName) {
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(xmlName));
            Element element = document.getRootElement();
            Map<String, String> maps = new HashMap<String, String>();
            List<Element> elements = element.elements();
            for (Element element1 : elements) {
                if (element1.getName().equals("table")) {
                    List<Element> list = element1.elements();
                    String str = "";
                    for (Element sjclubds : list) {
                        if (sjclubds.getName().equals("sjclubd")) {
                            String sjclubd = listNodes(sjclubds, "sjclubd");
                            str += sjclubd + ",";
                        }
                    }
                    str = str.substring(0, str.length() - 1);
                    maps.put(element1.attributeValue("name"), str);
                }
            }
            return maps;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    //遍历当前节点下的所有节点
    private static String listNodes(Element node, String params) {
        String str = "";
        String noteName = node.getName();
        if (noteName.equals(params)) {
            //首先获取当前节点的所有属性节点
            str = node.attributeValue("name") + "  " + node.attributeValue("type");
        }
        return str;
    }

    //遍历当前节点下的所有节点
    private static String listNodes2(Element node, String params) {
        String str = "";
        String noteName = node.getName();
        if (noteName.equals(params)) {
            //首先获取当前节点的所有属性节点
            str = node.attributeValue("name");
        }
        return str;
    }

    //根据表名获取所有的字段
    public static Map<String, List<String>> readXmlByVlaue(String xmlName, String Value) {
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(xmlName));
            Element element = document.getRootElement();
            Map<String, List<String>> maps = new HashMap<String, List<String>>();
            List<Element> elements = element.elements();
            for (Element element1 : elements) {
                if (element1.attributeValue("value").equals(Value)) {
                    List<Element> list = element1.elements();
                    List<String> values = new ArrayList<String>();
                    for (Element sjclubds : list) {
                        String sjclubd = listNodes2(sjclubds, "sjclubd");
                        if (!"".equals(sjclubd)) {
                            values.add(sjclubd);
                        }

                    }
                    maps.put(element1.attributeValue("name"), values);
                }
            }
            return maps;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据表名获取所有的字段
    public static List<String> readXmlByName(String xmlName, String name) {
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(xmlName));
            Element element = document.getRootElement();
            List<String> values = new ArrayList<String>();
            List<Element> elements = element.elements();
            for (Element element1 : elements) {
                if (element1.attributeValue("name").equals(name)) {
                    List<Element> list = element1.elements();
                    for (Element sjclubds : list) {
                        String sjclubd = listNodes2(sjclubds, "sjclubd");
                        if (!"".equals(sjclubd)) {
                            values.add(sjclubd);
                        }
                    }
                }
            }
            return values;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据字段获取索引位置
    public static String getByFiledName(String xmlName, String sjclubdName, String tableName) {
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(xmlName));
            Element element = document.getRootElement();
            List<Element> elements = element.elements();
            for (Element element1 : elements) {
                if (element1.attributeValue("name").equals(tableName)) {
                    List<Element> element1s = element1.elements();
                    for (Element element2 : element1s) {
                        if (element2.getName().equals("sjclubd")) {
                            if (element2.attributeValue("name").equals(sjclubdName)) {
                                return element2.attributeValue("index");
                            }
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取表下面的字段和索引
    public static Map<String, String> getListMap(Document document, String tableName) {
        Map<String, String> maps = new HashMap<String, String>();
        Element element = document.getRootElement();
        List<Element> elements = element.elements();
        for (Element element1 : elements) {
            if (element1.attributeValue("name").equals(tableName)) {
                List<Element> element1s = element1.elements();
                Map<String, String> map = new HashMap<String, String>();
                for (Element element2 : element1s) {
                    if (element2.getName().equals("sjclubd")) {
                        //if(!"".equals(element2.attributeValue("index"))){
                            maps.put(element2.attributeValue("value"), element2.attributeValue("index"));
                       // }
                    }
                }
            }
        }
        return maps;
    }

    //获取表下面的特殊字段和索引
    public static Map<String, String> getTitleFiled(Document document, String tableName) {
        Map<String, String> maps = new HashMap<String, String>();
        Element element = document.getRootElement();
        List<Element> elements = element.elements();
        for (Element element1 : elements) {
            if (element1.attributeValue("name").equals(tableName)) {
                List<Element> element1s = element1.elements();
                Map<String, String> map = new HashMap<String, String>();
                for (Element element2 : element1s) {
                    if (element2.getName().equals("title")) {
                        List<Element> elements3=element2.elements();
                        for(Element element3:elements3){
                            String sjclubd = element3.attributeValue("name");
                            String index = getByTitleNameIndex(document, sjclubd, tableName);
                            maps.put(element3.attributeValue("value"), index);
                        }

                    }
                }
            }
        }
        return maps;
    }


    //根据字段获取索引位置
    public static String getByFiledName(Document document, String sjclubdName, String tableName) {

        Element element = document.getRootElement();
        List<Element> elements = element.elements();
        for (Element element1 : elements) {
            if (element1.attributeValue("name").equals(tableName)) {
                List<Element> element1s = element1.elements();
                for (Element element2 : element1s) {
                    if (element2.getName().equals("sjclubd")) {
                        if (element2.attributeValue("name").equals(sjclubdName)) {
                            return element2.attributeValue("index");
                        }
                    }
                }
            }
        }
        return null;
    }


    public static String getByTitleNameIndex(String path, String name, String tableName) {
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(path));
            Element element = document.getRootElement();

            List<Element> elements = element.elements();
            for (Element element1 : elements) {
                if (element1.attributeValue("name").equals(tableName)) {
                    List<Element> element1s = element1.elements();
                    for (Element element2 : element1s) {
                        if (element2.getName().equals("title")) {
                            List<Element> element2s = element2.elements();
                            for (Element element3 : element2s) {
                                if (element3.attributeValue("name").equals(name)) {
                                    return element3.attributeValue("cellValueIndex");
                                }
                            }
                        }

                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getByTitleNameIndex(Document document, String name, String tableName) {


        Element element = document.getRootElement();

        List<Element> elements = element.elements();
        for (Element element1 : elements) {
            if (element1.attributeValue("name").equals(tableName)) {
                List<Element> element1s = element1.elements();
                for (Element element2 : element1s) {
                    if (element2.getName().equals("title")) {
                        List<Element> element2s = element2.elements();
                        for (Element element3 : element2s) {
                            if (element3.attributeValue("name").equals(name)) {
                                return element3.attributeValue("cellValueIndex");
                            }
                        }
                    }

                }
            }
        }

        return null;
    }


    public static String getTableName(String xmlName, String value) {
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(xmlName));
            Element element = document.getRootElement();

            List<Element> elements = element.elements();
            for (Element element1 : elements) {
                if (element1.attributeValue("value").equals(value)) {
                    return element1.attributeValue("name");
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取xml定义的当前表的标题

    /**
     * @param xmlName 读取xml路径
     * @param name    匹配表的名称
     * @return 标题名字和位置
     */
    public static List<Map<String, String>> getByTitleNameIndex(String xmlName, String name) {
        try {
            //创建SAXReader对象
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(xmlName));
            Map<String, String> map;
            Element element = document.getRootElement();
            List<Element> elements = element.elements();
            for (Element element1 : elements) {
                if (element1.attributeValue("name").equals(name)) {
                    List<Element> element1s = element1.elements();
                    for (Element element2 : element1s) {
                        if (element2.getName().equals("title")) {
                            List<Element> element2s = element2.elements();
                            for (Element element3 : element2s) {
                                Map<String, String> map1 = new HashMap<String, String>();
                                map1.put(element3.attributeValue("name"), element3.attributeValue("cellValueIndex"));
                                list.add(map1);
                            }
                        }

                    }
                }

            }
            return list;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Map<String, String>> getByTitleNameIndex(Document document, String name) {

        //创建SAXReader对象
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
/*            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(xmlName));*/
        Map<String, String> map;
        Element element = document.getRootElement();
        List<Element> elements = element.elements();
        for (Element element1 : elements) {
            if (element1.attributeValue("name").equals(name)) {
                List<Element> element1s = element1.elements();
                for (Element element2 : element1s) {
                    if (element2.getName().equals("title")) {
                        List<Element> element2s = element2.elements();
                        for (Element element3 : element2s) {
                            Map<String, String> map1 = new HashMap<String, String>();
                            map1.put(element3.attributeValue("name"), element3.attributeValue("cellValueIndex"));
                            list.add(map1);
                        }
                    }

                }
            }

        }
        return list;

    }


    public static Map<String, List<String[]>> getFiledAndIndexByValue(String xmlName, String Value) {
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(xmlName));
            Element element = document.getRootElement();
            Map<String, List<String[]>> maps = new HashMap<String, List<String[]>>();
            List<Element> elements = element.elements();
            for (Element element1 : elements) {
                if (element1.attributeValue("name").equals(Value)) {
                    List<Element> list = element1.elements();
                    List<String[]> values = new ArrayList<String[]>();
                    for (Element sjclubd : list) {
                        if (!"".equals(sjclubd.attributeValue("index"))) {
                            String sjclubds[] = new String[]{listNodes2(sjclubd, "sjclubd"),
                                    sjclubd.attributeValue("index")
                            };
                            if (!"".equals(sjclubds[0]) && !"".equals(sjclubds[1])) {
                                values.add(sjclubds);
                            }
                        }
                    }
                    maps.put(element1.attributeValue("name"), values);
                }
            }
            return maps;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    //修改xml
    public static void setIndex(String path, String tableName, String value, String index, String type) throws Exception {
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        //读取文件 转换成Document
        Document document = reader.read(new File(path));
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            if (element.attributeValue("name").equals(tableName)) {
                List<Element> element1s = element.elements();
                for (Element element1 : element1s) {
                    if ("sjclubd".equals(type)) {
                        if ("sjclubd".equals(element1.getName())) {
                            String val = element1.attributeValue("value");
                            if (val.equals(value)) {
                                Attribute attribute = element1.attribute("index");
                                attribute.setValue(index);
                            }
                        }
                    }
                    if ("title".equals(type)) {
                        if ("title".equals(element1.getName())) {
                            List<Element> elementList = element1.elements();
                            for (Element element2 : elementList) {
                                String val = element2.attributeValue("value");
                                if (val!=null&&val.equals(value)) {
                                    Attribute attribute = element2.attribute("cellValueIndex");
                                    attribute.setValue(index);
                                }
                            }
                        }
                    }
                }
            }
        }
        writeXml(document, path);
    }

    /**
     * 得到XML文档
     *
     * @param xmlFile
     *            文件名（路径）
     * @return XML文档对象
     * @throws DocumentException
     */
    public static Document getDocument(String xmlFile) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        File sjclub = new File(xmlFile);
        try {
            if (!sjclub.exists()) {
                return null;
            } else {
                return reader.read(sjclub);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e + "->指定文件【" + xmlFile + "】读取错误");
        }
    }

    /**
     * 输出xml文件
     *
     * @param document
     * @param sjclubPath
     * @throws IOException
     */
    public static void writeXml(Document document, String sjclubPath) throws IOException {
        File xmlFile = new File(sjclubPath);
        XMLWriter writer = null;
        try {
            if (xmlFile.exists())
                xmlFile.delete();
            writer = new XMLWriter(new FileOutputStream(xmlFile), OutputFormat.createPrettyPrint());
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     *    删除属性等于某个值的元素
     *    @param document  XML文档
     *    @param xpath xpath路径表达式
     *    @param attrName 属性名
     *    @param attrValue 属性值
     *    @return
     */
    @SuppressWarnings("unchecked")
    public static Document deleteElementByAttribute(Document document, String xpath, String parentName, String attrName, String attrValue) {
        Iterator<Object> iterator = document.selectNodes(xpath).iterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            Element parentElement = element.getParent();
            String parentValue=parentElement.attributeValue("name");
            if(parentValue.equals(parentName)){
                // 根据属性名获取属性值
                List<Element> elements=element.elements();
                for(Element element1:elements){
                    Attribute attribute = element1.attribute(attrName);
                    if (attrValue.equals(attribute.getValue())) {
                        element.remove(element1);
                    }
                }
            }
        }
        return document;
    }

    /**
     * 增加xml文件节点
     *
     * @param document
     *            xml文档
     * @param elementName
     *            要增加的元素名称
     * @param attributeNames
     *            要增加的元素属性
     * @param attributeValues
     *            要增加的元素属性值
     */
    public static Document addElementByName(Document document, String parentName, String elementName, Map<String, String> attrs) {
        try {
            Element root = document.getRootElement();
            List<Element> elements= root.elements();
            for(Element element1:elements){
                String tableName=element1.attributeValue("name");
                if(parentName.equals(tableName)){
                    Element subElement = element1.addElement(elementName);
                    for (Map.Entry<String, String> attr : attrs.entrySet()) {
                        subElement.addAttribute(attr.getKey(), attr.getValue());
                    }
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e + "->指定的【" + elementName + "】节点增加出现错误");
        }
        return document;
    }
    //获取元素的属性
    public static Map<String,String> getElementsAttribute(Document document, String tableName, String xpath, String attrValue){
        Iterator<Object> iterator = document.selectNodes(xpath).iterator();
        Map<String,String> maps=new HashMap<String, String>();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            Element parenetElement= element.getParent();
            String name=parenetElement.attributeValue("name");
            if(tableName.equals(name)){
               List<Element> element1s= element.elements();
                for(Element element2:element1s){
                    String value= element2.attributeValue("value");
                    if(attrValue.equals(value)){
                        List<Attribute> attributes= element2.attributes();
                        for(Attribute attribute:attributes){
                            maps.put(attribute.getName(),attribute.getValue());
                        }
                    }
                }

            }
        }
        return maps;
    }

    public  static  void moveNode(String path, String type, String tableName, String sjclubdValue)throws Exception {
        Document document= getDocument(path);
        //删除标题行
        if("sjclubd".equals(type)){
            Map<String,String> maps= getElementsAttribute(document,tableName,"/resources/table/title",sjclubdValue);
            deleteElementByAttribute(document,"/resources/table/title",tableName,"value",sjclubdValue);
            //增加普通行
            addElementByName(document,tableName,"sjclubd",maps);
        }
        //删除普通行
        if("title".equals(type)){

            //deleteElementByAttribute(document,"/resources/table/sjclubd","value",sjclubdValue);
            //增加标题行

        }
        writeXml(document,path);
    }
}
