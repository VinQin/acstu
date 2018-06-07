package edu.stu.util;

import edu.stu.bean.RandomUser;
import edu.stu.dao.DBHelper;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class UserControl {

    private DBHelper dbHelper = new DBHelper();
    private RandomUser randomUser = null;

    public RandomUser getRandomUser() {
        int band = 10; // 初始随机数范围
        String bandsql = "SELECT COUNT(*) FROM stu_users;";
        dbHelper.getPst(bandsql);

        try {
            ResultSet res = dbHelper.pst.executeQuery();
            if (res.next()) {
                band = res.getInt(1); // 以stu_users中的行数作为新的随机数范围
            }
            res.close();
            dbHelper.pst.close();

            int id = new Random().nextInt(band); // 获得随机数
            while (!getRandomUser(id)) {
                id = new Random().nextInt(band);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return randomUser;
    }

    private boolean getRandomUser(int id) throws SQLException {
        String sql = "SELECT userName,pwd,comments from stu_users where tag=true and id=" + id;
        dbHelper.getPst(sql);
        ResultSet res = dbHelper.pst.executeQuery();
        boolean flag = false;

        if (res.next()) {
            randomUser =
                    new RandomUser(
                            id,
                            res.getString("userName"),
                            res.getString("pwd"),
                            true,
                            res.getString("comments"));
            flag = true;
        }

        res.close();
        dbHelper.pst.close();

        return flag;
    }

    /*@param
     *id stu_users表中的id
     */
    public void updateUser(int id, String msg) {
        String comments = "user is validated"; // 默认认为编号为id的这个用户是有效用户
        try {
            //comments = new String(msg.getBytes("iso-8859-1"), "utf-8");
            comments = new String(msg.getBytes("utf-8"), "utf-8");
            System.out.println(comments);
        } catch (UnsupportedEncodingException e) {
            System.out.println("edu.stu.util.UserControl.updateUser: UnsupportedEncodingException");
            e.printStackTrace();
        }
        String sql =
                "UPDATE stu_users SET tag=false,comments='" + comments + "' where id=" + id + ";";
        dbHelper.getPst(sql);
        try {
            dbHelper.pst.executeUpdate();
            dbHelper.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            dbHelper.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
