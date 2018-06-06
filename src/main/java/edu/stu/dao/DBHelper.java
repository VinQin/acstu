package edu.stu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
    public static final String url = "jdbc:mysql://10.39.27.88/ac?useSSL=false&characterEncoding=UTF-8";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "acmanipulator";
    public static final String password = "Professorcai";

    public Connection conn = null;
    public PreparedStatement pst = null;


    public DBHelper() {
        try {
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPst(String sql) {
        try {
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.pst.close();
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 
