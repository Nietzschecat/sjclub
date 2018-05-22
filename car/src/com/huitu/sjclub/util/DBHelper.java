package com.huitu.sjclub.util;

import java.io.IOException;
import java.sql.*;

/**
 * Created by cys on 2017/8/3.
 * 数据库操作
 *
 */
public class DBHelper {
    private  static Connection connection;
    private static Statement statement;

    //生成文件的路径
    public static void openConn(String path, String dbName) throws ClassNotFoundException,SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite://"+path+"/"+dbName+".rar");
        connection.setAutoCommit(true);
        statement = connection.createStatement();
    }
    public static void find(String path) throws ClassNotFoundException,SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:"+path);
        connection.setAutoCommit(true);
        statement = connection.createStatement();
    }
    public static Connection openConnection(String path)throws ClassNotFoundException,SQLException {
        Class.forName("org.sqlite.JDBC");
       return  DriverManager.getConnection("jdbc:sqlite:"+path);
    }
    public static void delete(String tableName)throws SQLException {
        String sql="delete from "+tableName;
        statement.execute(sql);
    }
    public static void  insert(String tableName, String names, String values)throws SQLException {
        String sql = "INSERT INTO "+tableName+" ("+names+") " +"VALUES ("+values+")";
        System.out.println(sql);
        int flag=statement.executeUpdate(sql);
    }
    public static void create(String table, String filds)throws IOException,SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " +table+
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL ," +filds+")";
        statement.executeUpdate(sql);
    }
    public static ResultSet queryByTableName(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery( "SELECT * FROM "+tableName+";" );
        return rs;
    }
    public static void close()throws SQLException {
        statement.close();
        connection.close();
    }
    public  static int count(String tableName)throws SQLException {
        ResultSet rs =  statement.executeQuery("select count(*) from "+tableName);
        rs.next();
        int i = rs.getInt(1);
        return i;
    }


    public static ResultSet queryByParams(String tableName, String name, String name1, String value, String value1)throws SQLException {
        ResultSet rs =  statement.executeQuery("SELECT * FROM "+tableName+" WHERE "+name1+"='"+value1+"'AND "+name+"='"+value+"';");
        return rs;
    }
    /* 判断某张表是否存在
    * @param tabName 表名
    * @return
            */
    public static boolean tabbleIsExist(String tableName) throws SQLException {
        boolean result = false;
        if(tableName == null){
            return false;
        }
        //这里表名可以是Sqlite_master
        String sql = "select count(*) as c from '"+tableName.trim()+"'";
        return  statement.execute(sql);

    }

    /***
     *
     * @param s
     * @param visitorName
     * @return
     */
    public static ResultSet queryByTableNameByName(String tableName, String visitorName)throws SQLException {
        String sql="SELECT count(*) FROM "+tableName.trim()+" where  user_name='"+visitorName.trim()+"';";
        System.out.println(sql);
        return  statement.executeQuery(sql);
    }
}
