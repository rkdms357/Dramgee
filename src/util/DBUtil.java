package util;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DBUtil {

    public static Connection dbconnect() {
        Connection conn = null;
        Properties pro = new Properties();
        String path = "oracleDB.properties";
        InputStream is = DBUtil.class.getResourceAsStream(path);

        try {
            pro.load(is);
            String driver = pro.getProperty("driver");
            String url = pro.getProperty("url");
            String id = pro.getProperty("username");
            String pass = pro.getProperty("password");

            Class.forName(driver);

            conn = DriverManager.getConnection(url, id, pass);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void dbDisconnect(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}