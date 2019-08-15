package com.jdbc;

import java.sql.*;

public class Dbconnection {

    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String url1 = "jdbc:mysql://192.168.30.16:3306/?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true";
    private static final String username = "selector";
    private static final String password = "ZXC@ict2019.";

    private Dbconnection() {
    }

    static {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url1, username, password);
        return connection;
    }

    public static Connection getConnection(String database) throws SQLException {
        String url = "jdbc:mysql://192.168.30.16:3306/"+database+"?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true";
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    //按顺序释放资源
    public static void close(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
         String url = "jdbc:mysql://192.168.30.16:3306/?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true";
         String username = "selector";
         String password = "ZXC@ict2019.";
        Connection connection=null;
        try {
           connection = Dbconnection.getConnection(url);
            if (connection!=null){
                System.out.println("success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
