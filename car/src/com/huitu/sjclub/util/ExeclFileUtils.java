package com.huitu.sjclub.util;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13.
 * 操作文件，读取execl
 */
public class ExeclFileUtils {

    //获取sheet表
    /*public static List<Map<String,Object>> getSheets(String path)throws Exception{
       List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        File sjclub=new File(path);
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(sjclub));
        int sheets=book.getNumberOfSheets();

        for(int i=0;i<sheets;i++){
            HSSFSheet sheet=book.getSheetAt(i);
            //path=path.substring(path.lastIndexOf("\\")+1,path.length());
            DBHelper.openConn("sys");
            ResultSet rs= DBHelper.queryByParams("sheet", "sjclubName", "sheetName", path, sheet.getSheetName());
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("sheetName",sheet.getSheetName());
            while (rs.next()){
                int startRow = rs.getInt("startRow");
                map.put("startRow",startRow);
                int startCell = rs.getInt("startCell");
                map.put("startCell",startCell);
                int _id = rs.getInt("_id");
                map.put("_id",_id);
            }
            list.add(map);
            DBHelper.close();
        }

        return list;
    }*/
    //读取标题
    public static Map<String,Integer> getDataListTile(String sheetName, int startRow, int startCell, Workbook book)throws Exception {
        Map<String,Integer> titleMaps=new HashMap<String,Integer>();
        Sheet sheet = null;
        if("".equals(sheetName)||null==sheetName){
            sheet=book.getSheetAt(0);
       }else{
            sheet=book.getSheet(sheetName);
       }
       Row row=sheet.getRow(0);
        for(int j=startCell;j<row.getLastCellNum();j++){
            Object object =getCellValue(row.getCell(j));
            titleMaps.put((String)object,j);
        }
        return titleMaps;
    }
    //读取数据
    public static List<List<Object>> getDataList(String sheetName, int startRow, int startCell, String path)throws Exception {
        List<List<Object>> list = new ArrayList<List<Object>>();
        // HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(ResourceUtils.getFile("classpath:"+path)));
        File sjclub=new File(path);
        //HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(sjclub));

        Workbook book = WorkbookFactory.create(new FileInputStream(sjclub));
        Sheet sheet = null;
        if (sheetName == null) {
            sheet = book.getSheetAt(0);
        }else {
            sheet = book.getSheet(sheetName);
        }
        if(sheet==null){
            sheet=book.getSheetAt(0);
        }
        if(sheet.getLastRowNum()==0){
            return list;
        }
        m:for(int i=startRow; i<sheet.getLastRowNum()+1; i++) {
            List<Object> list1=new ArrayList<Object>();
            Row row = sheet.getRow(i);
            if(row==null){
                break m;
            }
            for(int j=startCell;j<row.getLastCellNum();j++){
                Object object =getCellValue(row.getCell(j));
                if(0==j){
                    if(null==object){
                        break m;
                    }
                }
                list1.add(object);
            }
            list.add(list1);
        }
        System.out.println("共有 " + list.size() + " 条数据：");
        return list;
    }






    //读取数据
    public static List<List<Object>> getDataList(String sheetName, int startRow, int startCell, Workbook book)throws Exception {
        List<List<Object>> list = new ArrayList<List<Object>>();
        // HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(ResourceUtils.getFile("classpath:"+path)));
        Sheet sheet =null;
        if(sheetName==null){
            sheet=book.getSheetAt(0);
        }else{
            sheet=book.getSheet(sheetName);
        }
        if(sheet==null){
            sheet=book.getSheetAt(0);
        }
        if(sheet.getLastRowNum()==0){
            return list;
        }
        m:for(int i=startRow; i<sheet.getLastRowNum()+1; i++) {
            List<Object> list1=new ArrayList<Object>();
            Row row = sheet.getRow(i);
            for(int j=startCell;j<row.getLastCellNum();j++){
                Object object =getCellValue(row.getCell(j));
                if(0==j){
                    if(null==object){
                        break m;
                    }
                }
                list1.add(object);
            }
            list.add(list1);

        }

        System.out.println("共有 " + list.size() + " 条数据：");
        return list;
    }







    //判断单元格的值
    private static Object getCellValue(Cell hssfCell){
       /* CELL_TYPE_NUMERIC 数值型 0
        CELL_TYPE_STRING 字符串型 1
        CELL_TYPE_FORMULA 公式型 2
        CELL_TYPE_BLANK 空值 3
        CELL_TYPE_BOOLEAN 布尔型 4
        CELL_TYPE_ERROR 错误 5*/
        if(hssfCell==null){
            return "";
        }
        if(hssfCell.getCellType()== Cell.CELL_TYPE_NUMERIC){
            if(DateUtil.isCellDateFormatted(hssfCell)){
                return hssfCell.getDateCellValue();
            }else{
                DecimalFormat df = new DecimalFormat("#");
                String str= df.format(hssfCell.getNumericCellValue());
                if(str.indexOf(".")!=-1){
                    String sxff=str.substring(str.indexOf(".")+1,str.length());
                    Integer int_sxff= Integer.parseInt(sxff);
                    //System.out.println("尾数:"+int_sxff);
                    if(int_sxff==0){
                        Double d= Double.parseDouble(str);
                        Integer i = d.intValue();
                        return i;
                    }else{
                        return hssfCell.getNumericCellValue();
                    }
                }else{
                    return str;
                }

            }
        }
        if(hssfCell.getCellType()== Cell.CELL_TYPE_STRING){
            return hssfCell.getStringCellValue();
        }
        /*if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            return hssfCell.getDateCellValue();
        }*/
        if(hssfCell.getCellType()== Cell.CELL_TYPE_BLANK){
            return null;
        }
        if(hssfCell.getCellType()== Cell.CELL_TYPE_BOOLEAN){
            return hssfCell.getBooleanCellValue();
        }
        if(hssfCell.getCellType()== Cell.CELL_TYPE_ERROR){
            return null;
        }
        return null;
    }

    //读取excel指定的位置
    public static Object getCell(String sheetName, String path, String row, String cell)throws Exception {
        File sjclub=new File(path);
        Workbook book = WorkbookFactory.create(new FileInputStream(sjclub));
        Sheet sheet=null;
        if("".equals(sheetName)){
            sheet=book.getSheetAt(0);
        }else{
            sheet=book.getSheet(sheetName);
        }
        Integer rowIndex= Integer.parseInt(row);
        Row row1=sheet.getRow(rowIndex);
        Integer cellIndex= Integer.parseInt(cell);
        Cell cell1=row1.getCell(cellIndex);

        return getCellValue(cell1);
    }




    //读取excel指定的位置
    public static Object getCell(String sheetName, Workbook book, String row, String cell)throws Exception {

        Sheet sheet=null;
        if("".equals(sheetName)){
            sheet=book.getSheetAt(0);
        }else{
            sheet=book.getSheet(sheetName);
        }
        Integer rowIndex= Integer.parseInt(row);
        Row row1=sheet.getRow(rowIndex);
        Integer cellIndex= Integer.parseInt(cell);
        Cell cell1=row1.getCell(cellIndex);

        return getCellValue(cell1);
    }



}
