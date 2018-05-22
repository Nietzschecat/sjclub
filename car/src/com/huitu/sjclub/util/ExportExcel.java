package com.huitu.sjclub.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhongzhouping on 2017/8/7.
 */
public class ExportExcel {

    private List<String[]> head; //表头数据

    private List<String[]> data; //数据

    private String path; //模板路径

    private Integer MAX_SIZE=60000;
    private Integer MAX_XLSX_SIZE=1048000;

    private HttpServletResponse response; //HTTP响应请求
    private Integer startRow;
    private List<String> titles;

    private static Map<String,String> map;
    static{
        map=new HashMap<String,String>();
        for(int i=0;i<26;i++){
            char c=(char)('A'+i);
            String key= String.valueOf(c);
            String value= String.valueOf(i);
            map.put(key,value);
        }

        for(int i=0;i<26;i++){
            char c=(char)('A'+i);
            String key= String.valueOf(c);
            String value= String.valueOf(i+26);
            map.put("A"+key,value);
        }
    }

    /**
     * 导出Excel 通过弹出下拉框模式
     * @param data 数据
     * @param head 表头数据
     * @param path 模板路径
     * @param response HTTP响应请求
     */
    public ExportExcel(List<String[]> data, List<String[]> head, String path, HttpServletResponse response, Integer startRow){
        this.head = head;
        this.data = data;
        this.path = path;
        this.response = response;
        this.startRow=startRow;
    }
    public ExportExcel(List<String[]> data, String path, HttpServletResponse response, Integer startRow){
        this.data = data;
        this.path = path;
        this.response = response;
        this.startRow=startRow;
    }
    public ExportExcel(List<String> titles, HttpServletResponse response){
        this.titles = titles;
        this.response = response;
    }

    public String exportNotTitle(String realName) throws Exception {
        FileInputStream in = new FileInputStream(path);
        HSSFWorkbook workbook = new HSSFWorkbook(in);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        for (int i = 0; i < data.size(); i++) {
            String[] str = data.get(i);//遍历每个对象
            HSSFRow row2 = sheet.getRow(startRow + i);//创建所需的行数
            for (int j = 0; j < str.length; j++) {
                if(row2==null){
                    row2=sheet.createRow(startRow + i);
                }
                HSSFCell cell2 = row2.getCell(j);
                if(cell2==null){
                    cell2=row2.createCell(j);
                }
                cell2.setCellValue(str[j]);
            }
        }
//        System.out.println(realName);
        FileOutputStream out = new FileOutputStream(realName);
        workbook.write(out);
        return  realName;
    }


    /**
     * 数据导出
     * @throws Exception
     */
    public  void export(){
        try {
            FileInputStream in = new FileInputStream(path);
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;
            for(int i = 0; i < head.size(); i++){
                String[] indexs = head.get(i)[1].split(",");
                row = sheet.getRow(Integer.parseInt( indexs[0]));
                cell = row.getCell(Integer.parseInt(map.get(indexs[1])));
                cell.setCellValue(head.get(i)[0]);
            }

            for(int i = 0;i < data.size(); i++){
                String[] str = data.get(i);//遍历每个对象
                HSSFRow row2 = sheet.getRow(startRow+i);//创建所需的行数
                for(int j = 0; j < str.length; j++) {
                    HSSFCell cell2= row2.getCell(j);

                    if(cell2==null){
                        cell2=row2.createCell(j);
                    }
                    cell2.setCellValue(str[j]);
                }
            }
            String sjclubName = "Excel-"+ UUID.randomUUID()+".xls";
            String headStr = "attachment; sjclubname=\"" + sjclubName + "\"";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", headStr);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 数据导出
     * @throws Exception
     */
    public String generateExcel(String realName) throws Exception {

        FileInputStream in = new FileInputStream(path);
        HSSFWorkbook workbook = new HSSFWorkbook(in);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        for (int i = 0; i < head.size(); i++) {
            String[] indexs = head.get(i)[1].split(",");
            row = sheet.getRow(Integer.parseInt(indexs[0]));
            cell = row.getCell(Integer.parseInt(map.get(indexs[1])));
            cell.setCellValue(head.get(i)[0]);
        }

        for (int i = 0; i < data.size(); i++) {
            String[] str = data.get(i);//遍历每个对象
            HSSFRow row2 = sheet.getRow(startRow + i);//创建所需的行数
            for (int j = 0; j < str.length; j++) {

                if(row2==null){
                    row2=sheet.createRow(startRow + i);
                }
                HSSFCell cell2 = row2.getCell(j);

                if(cell2==null){
                    cell2=row2.createCell(j);
                }

                cell2.setCellValue(str[j]);
            }
        }
//        System.out.println(realName);
        FileOutputStream out = new FileOutputStream(realName);
        workbook.write(out);

        return  realName;
    }

    /**
     * 数据导出
     * @throws Exception
     */
    public String generateExcel(List<String[]> data, String sheetName, List<String[]> data1, String sheetName1, String realName) throws Exception {
        //MAX_SIZE

        HSSFWorkbook workbook=new HSSFWorkbook();
        createSheet(workbook,sheetName,data);
        createSheet(workbook,sheetName1,data1);
        /*HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("已核查");
        HSSFRow row = sheet.createRow(0);//标题行
        for(int i=0;i< titles.size();i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
        }
        for (int i = 0; i < data.size(); i++) {
            String[] str = data.get(i);//遍历每个对象
            HSSFRow row2 = sheet.createRow(1 + i);//
            for (int j = 0; j < str.length; j++) {
                HSSFCell cell2 = row2.createCell(j);
                cell2.setCellValue(str[j]);
            }
        }


        HSSFSheet sheet1 = workbook.createSheet("未核查");
        HSSFRow row1 = sheet1.createRow(0);//标题行
        for(int i=0;i< titles.size();i++){
            HSSFCell cell = row1.createCell(i);
            cell.setCellValue(titles.get(i));
        }
        for (int i = 0; i < data1.size(); i++) {
            String[] str = data1.get(i);//遍历每个对象
            HSSFRow row2 = sheet1.createRow(1 + i);//
            for (int j = 0; j < str.length; j++) {
                HSSFCell cell2 = row2.createCell(j);
                cell2.setCellValue(str[j]);
            }
        }*/
//        System.out.println(realName);
        FileOutputStream out = new FileOutputStream(realName);
        workbook.write(out);

        return  realName;
    }
    public String generateXlsxExcel(List<String[]> data, String sheetName, String realName)throws Exception {
        File sjclub=new File(realName);
        Workbook  workbook=  new SXSSFWorkbook();
        createXlsxSheet(workbook,sheetName,data);
        FileOutputStream out = new FileOutputStream(realName);
        workbook.write(out);

        return  realName;
    }
    //导出已核实的数据.xlsx
    private void createXlsxSheet(Workbook xssfWorkbook, String sheetName, List<String[]> data){
        int size=0;
        if(data.size()%MAX_XLSX_SIZE!=0){
            size=(data.size()/MAX_XLSX_SIZE)+1;
        }
        // System.out.println(size);
        if(size==0){
            Sheet sheet = xssfWorkbook.createSheet(sheetName+0);
            Row row = sheet.createRow(0);//标题行
            for(int t=0;t< titles.size();t++){
                Cell cell = row.createCell(t);
                cell.setCellValue(titles.get(t));
            }
        }
        for(int i=0;i<size;i++){
            //System.out.println(sheetName+i);
            Sheet sheet = xssfWorkbook.createSheet(sheetName+i);

            Row row = sheet.createRow(0);//标题行
            for(int t=0;t< titles.size();t++){
                Cell cell = row.createCell(t);
                cell.setCellValue(titles.get(t));
            }
            //i*MAX_SIZE
            //第一个阶段
            int sum= data.size()-(data.size()-((i+1)*MAX_XLSX_SIZE));
            if(data.size()<=((i+1)*MAX_XLSX_SIZE)){
                sum=data.size()-(i*MAX_XLSX_SIZE);
            }
            //System.out.println(">>>" + sum);
            for (int s = 0; s < sum; s++) {
                String[] str = data.get(s+i*MAX_XLSX_SIZE);//遍历每个对象
                // System.out.println("数字"+s);
                //System.out.println(1 + (s-i*MAX_SIZE));
                Row row2 = sheet.createRow(1 + s);//
                for (int j = 0; j < str.length; j++) {
                    Cell cell2 = row2.createCell(j);
                    cell2.setCellValue(str[j]);
                }
            }
        }
    }
    private void createSheet(HSSFWorkbook workbook, String sheetName, List<String[]> data){
        int size=0;
        if(data.size()%MAX_SIZE!=0){
            size=(data.size()/MAX_SIZE)+1;
        }
       // System.out.println(size);

        for(int i=0;i<size;i++){
            //System.out.println(sheetName+i);
            HSSFSheet sheet = workbook.createSheet(sheetName+i);
            HSSFRow row = sheet.createRow(0);//标题行
            for(int t=0;t< titles.size();t++){
                HSSFCell cell = row.createCell(t);
                cell.setCellValue(titles.get(t));
            }
            //i*MAX_SIZE
            //第一个阶段
            int sum= data.size()-(data.size()-((i+1)*MAX_SIZE));
            if(data.size()<=((i+1)*MAX_SIZE)){
                sum=data.size()-(i*MAX_SIZE);
            }
            //System.out.println(">>>" + sum);
            for (int s = 0; s < sum; s++) {
                String[] str = data.get(s+i*MAX_SIZE);//遍历每个对象
               // System.out.println("数字"+s);
                //System.out.println(1 + (s-i*MAX_SIZE));
                HSSFRow row2 = sheet.createRow(1 + s);//
                for (int j = 0; j < str.length; j++) {
                    HSSFCell cell2 = row2.createCell(j);
                    cell2.setCellValue(str[j]);
                }
            }
        }
    }
}
