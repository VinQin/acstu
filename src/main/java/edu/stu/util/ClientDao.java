package edu.stu.util;

import edu.stu.bean.ClientInfo;
import edu.stu.dao.DBHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDao {
    public static void addClient(ClientInfo clientInfo) {
        String sql = "INSERT INTO tbl_client (username, date, ip_source, tag) VALUES(?, ?, ?, ?)";
        DBHelper dbHelper = new DBHelper();
        dbHelper.setPst(sql);
        PreparedStatement pst = dbHelper.pst;
        try {
            pst.setString(1, clientInfo.getUserName());
            pst.setObject(2, clientInfo.getDate());
            pst.setString(3, clientInfo.getIp());
            pst.setBoolean(4, clientInfo.getTag());

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbHelper.close();
    }
}
