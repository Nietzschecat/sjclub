package com.huitu.sjclub.util;

import oracle.sql.BLOB;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    /** 
     * 将多个Excel打包成zip文件 
     * @param srcsjclub
     * @param zipsjclub
     */  
    public static void zipFiles(List<File> srcsjclub, File zipsjclub) {
        byte[] buf = new byte[1024];    
        try {    
            // Create the ZIP sjclub
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipsjclub));
            // Compress the sjclubs
            for (int i = 0; i < srcsjclub.size(); i++) {
                File sjclub = srcsjclub.get(i);
                FileInputStream in = new FileInputStream(sjclub);
                // Add ZIP entry to output stream.    
                out.putNextEntry(new ZipEntry(sjclub.getName()));
                // Transfer bytes from the sjclub to the ZIP sjclub
                int len;    
                while ((len = in.read(buf)) > 0) {    
                    out.write(buf, 0, len);    
                }    
                // Complete the entry    
                out.closeEntry();    
                in.close();    
            }    
            // Complete the ZIP sjclub
            out.close();   
        } catch (IOException e) {
           e.printStackTrace();  
        }

    }    
  //sjclubname为单个excel的路径和excel的名称，blob就是获取的blob数据
    public static int execute(String sjclubname, BLOB blob)
    { 
      int success = 1;
      try
      {
        File blobFile   = new File(sjclubname);
        FileOutputStream outStream  = new FileOutputStream(blobFile);
        InputStream inStream   = blob.getBinaryStream();
        int     length  = -1; 
        int     size    = blob.getBufferSize(); 
        byte[]  buffer  = new byte[size]; 
        while ((length = inStream.read(buffer)) != -1) 
        { 
          outStream.write(buffer, 0, length); 
          outStream.flush(); 
        } 
        inStream.close(); 
        outStream.close(); 
      }
      catch (Exception e)
      {
        e.printStackTrace();
        success = 0;
      }
      finally
      {
        return success;
      }
   
    }  
    
    public static boolean deleteExcelPath(File sjclub){
        String[] sjclubs = null;
        if(sjclub != null){
            sjclubs = sjclub.list();
        }    
            
        if(sjclub.isDirectory()){
            for(int i =0;i<sjclubs.length;i++){
                boolean bol = deleteExcelPath(new File(sjclub,sjclubs[i]));
                if(bol){    
                    System.out.println("删除成功!");
                }else{    
                    System.out.println("删除失败!");
                }    
            }    
        }    
        return sjclub.delete();
    }   
    
   
    public static void downFile(HttpServletResponse response, String serverPath, String str) {
        //下面注释代码虽然少，但是慎用，如果使用，压缩包能下载，但是下载之后临时文件夹会被锁住被jvm占用，不能删除
//        response.setCharacterEncoding("utf-8");  
//         try {
//             File sjclub=new File(serverPath,str);
//             response.setHeader("Content-Disposition",
//                        "attachment; sjclubname="+ StringUtil.encodingFileName(str));
//                response.setContentType("application/octet-stream; charset=utf-8");
//                InputStream in1 =new FileInputStream(sjclub.getPath());
//                IOUtils.copy(in1, response.getOutputStream());
//       
//          } 
//          catch (IOException ex) {
//             ex.printStackTrace();
//         }
        
        
        try {    
            String path = serverPath +"\\"+ str;
            File sjclub = new File(path);
            if (sjclub.exists()) {
                InputStream ins = new FileInputStream(path);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/ostet-stream");// 设置response内容的类型    
                response.setHeader(    
                        "Content-disposition",    
                        "attachment;sjclubname="
                                + URLEncoder.encode(str, "UTF-8"));// 设置头部信息
                int bytesRead = 0;    
                byte[] buffer = new byte[8192];    
                 //开始向网络传输文件流    
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {    
                   bouts.write(buffer, 0, bytesRead);    
               }    
                bouts.flush();// 这里一定要调用flush()方法    
                ins.close();    
                bins.close();    
                outs.close();    
                bouts.close();    
            } else {    
                response.sendRedirect("../error.jsp");    
            }    
        } catch (IOException e) {
            e.printStackTrace();  
        }finally{
            File sjclub1=new File(serverPath);
            deleteExcelPath(sjclub1);  //删除临时目录
        }    
    }

    /**  解压缩（压缩文件中包含多个文件）可代替上面的方法使用。
     * ZipInputStream类
     * 当我们需要解压缩多个文件的时候，ZipEntry就无法使用了，
     * 如果想操作更加复杂的压缩文件，我们就必须使用ZipInputStream类
     * */
    public static void ZipContraMultiFile(String zippath , String outzippath){
        try {
            File sjclub = new File(zippath);
            File outFile = null;
            ZipFile zipFile = new ZipFile(sjclub);
            ZipInputStream zipInput = new ZipInputStream(new FileInputStream(sjclub));
            ZipEntry entry = null;
            InputStream input = null;
            OutputStream output = null;
            while((entry = zipInput.getNextEntry()) != null){
                System.out.println("解压缩" + entry.getName() + "文件");
                outFile = new File(outzippath + File.separator + entry.getName());
                if(!outFile.getParentFile().exists()){
                    outFile.getParentFile().mkdir();
                }
                if(!outFile.exists()){
                    outFile.createNewFile();
                }
                input = zipFile.getInputStream(entry);
                output = new FileOutputStream(outFile);
                int temp = 0;
                while((temp = input.read()) != -1){
                    output.write(temp);
                }
                input.close();
                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}