package com.jdbc;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class outputExcel1 {
    private static Connection connection = null;
    private static Connection conn = null;
    private static List<String> oslist =asList("information_schema","performance_schema","mysql");//系统表集合 去除

    static {
        try {
            connection = Dbconnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有数据库名
     *
     * @return
     * @throws SQLException
     */
    private static List<String> getDatabases() throws SQLException {
        List<String> list = new ArrayList<>();//存放数据库名
        PreparedStatement pre = null;
        ResultSet resultSet = null;
        String sql = "SHOW DATABASES";//查询数据库所有的表名
        pre = connection.prepareStatement(sql);
        resultSet = pre.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString("Database"));
        }
        Dbconnection.close(resultSet, pre, connection);
        return list;
    }

    /**
     * 查询单库所有表名
     *
     * @return
     * @throws SQLException
     */
    private static List<String> getTableNameList(String database) throws SQLException {
        List<String> list = new ArrayList<>();//存放表名
        conn = Dbconnection.getConnection(database);
        PreparedStatement pre = null;
        ResultSet resultSet = null;
        String sql = "select table_name from information_schema.tables where table_type = 'BASE TABLE' AND table_schema ='" + database + "'";//查询数据库所有的表名
        pre = conn.prepareStatement(sql);
        resultSet = pre.executeQuery();
//        list.add(database);//标识该表来自哪个数据库
        while (resultSet.next()) {
            list.add(resultSet.getString("table_name"));
//            System.out.println(resultSet.getString(1));
        }
        Dbconnection.close(resultSet, pre, null);
        return list;
    }


    /**
     * 查询所有表结构
     *
     * @param database
     * @return
     * @throws Exception
     */
    private static List<List<List<String>>> getListFromAis(List<String> database) throws Exception {
        List<List<List<String>>> listsum = new ArrayList<>();
        PreparedStatement pre = null;
        ResultSet resultSet = null;
        for (int n = 0; n < database.size(); n++) {
            //系统表直接忽略
            if (oslist.contains(database.get(n))){
                continue;
            }
            List<String> list = getTableNameList(database.get(n));
            System.out.println("数据库"+database.get(n)+"的大小为"+list.size());
            for (int i = 0; i < list.size(); i++) {
                //记录一张表
                List<List<String>> lists = new ArrayList<>();//记录一个表的结构 List<String>记录一张表结构的一行
                if ("L2_Port_Port_Journey_150601_180431_small_copy".equals(list.get(i))){
                    continue;
                }
                if ("L1_Ship_History_Positions_000000_999999".equals(list.get(i))){
                    continue;
                }
                if ("L1_Ship_History_Positions_000000_999999_copy1".equals(list.get(i))){
                    continue;
                }
                String sql = "SHOW COLUMNS FROM " + list.get(i);//直接拼接即可
                pre = conn.prepareStatement(sql);
                resultSet = pre.executeQuery();
                List<String> string = new ArrayList<>();
                string.add("三号机");
                string.add(database.get(n));//数据库名
                string.add(list.get(i));//表名
                lists.add(string);
                while (resultSet.next()) {
                    List<String> stringlist = new ArrayList<>();//记录表结构的第一行
                    stringlist.add(resultSet.getString(1));
                    stringlist.add(resultSet.getString(2));
                    stringlist.add(resultSet.getString(3));
                    stringlist.add(resultSet.getString(4));
                    stringlist.add(resultSet.getString(5));
                    lists.add(stringlist);
                }
                listsum.add(lists);
            }
        }
        Dbconnection.close(resultSet, pre, conn);
        return listsum;
    }

    /**
     * 写入excel
     *
     * @param lists
     * @return
     */
    public static String outPutExcel(List<List<List<String>>> lists) {
        Workbook wb = new XSSFWorkbook();
        String[] title = {"序号", "数据库名", "表名", "字段1", "类型", "key", "字段2", "类型", "key", "字段3", "类型", "key", "字段4", "类型", "key", "字段5", "类型", "key", "字段6", "类型", "key", "字段7",
                "类型", "key", "字段8", "类型", "key", "字段9", "类型", "key", "字段10", "类型", "key", "字段11", "类型", "key", "字段12", "类型", "key", "字段13", "类型",
                "key", "字段14", "类型", "key", "字段15", "类型", "key", "字段16", "类型", "key", "字段17", "类型", "key", "字段18", "类型", "key", "字段19", "类型", "key", "字段20", "类型", "key", "字段21",
                "类型", "key", "字段22", "类型", "key", "字段23", "类型", "key", "字段24", "类型", "key", "字段25", "类型", "key", "字段26", "类型", "key", "字段27", "类型",
                "key", "字段28", "类型", "key", "字段29", "类型", "key", "字段30", "类型", "key", "字段31", "类型", "key", "字段32", "类型", "key", "字段33", "类型", "key", "字段34"
                , "类型", "key", "字段1", "类型", "key", "字段1", "类型", "key", "字段1", "类型", "key", "字段1", "类型", "key", "字段1", "类型", "key", "字段1", "类型", "key",
                "字段35", "类型", "key", "字段36", "类型", "key", "字段37", "类型", "key", "字段38", "类型", "key", "字段39", "类型", "key", "字段40", "类型", "key", "字段41", "类型", "key", "字段42", "类型", "key",
                "字段43", "类型", "key", "字段44", "类型", "key", "字段45", "类型", "key", "字段46", "类型", "key", "字段47", "类型", "key", "字段48", "类型", "key", "字段49", "类型", "key"};
        //设置sheet名称，并创建新的对象
        String sheetname = "表结构总览";
        Sheet sheet = wb.createSheet(sheetname);
        //获取表头行
        Row titleRow = sheet.createRow(0);
        //创建单元格，设置style居中，字体，单元格大小等
        CellStyle style = wb.createCellStyle();
        Cell cell = null;
        //把已经写好的标题行写入excel文件中
        for (int i = 0; i < title.length; i++) {
            cell = titleRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //把从数据库中取得的数据一一写入excel文件中
        Row row = null;
        for (int i = 0; i < lists.size(); i++) {
            List<List<String>> rowlist = lists.get(i);
            //多少张表创建多少行
            row = sheet.createRow(i + 1);
            //把值一一写进单元格里
            //设置第一列为自动递增的序号
            for (int j = 0; j < rowlist.size(); j++) {
                int n = 3 * j;
                if (n == 0) {
                    row.createCell(n).setCellValue(rowlist.get(j).get(0));//Field
                    row.createCell(n + 1).setCellValue(rowlist.get(j).get(1));//Type
                    cell = row.createCell(n + 2);
                    cell.setCellValue(rowlist.get(j).get(2));//key
                    continue;
                }
                row.createCell(n).setCellValue(rowlist.get(j).get(0));//Field
                row.createCell(n + 1).setCellValue(rowlist.get(j).get(1));//Type
                cell = row.createCell(n + 2);
                if (rowlist.get(j).get(3) != null) {
                    cell.setCellValue(rowlist.get(j).get(3));//key
                } else {
                    cell.setCellValue("nokey");//key
                }
            }
        }
        //设置单元格宽度自适应，在此基础上把宽度调至1.5倍
        for (int i = 0; i < title.length; i++) {
            sheet.autoSizeColumn(i, true);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }
        //获取配置文件中保存对应excel文件的路径，本地也可以直接写成F：excel/stuInfoExcel路径
        String folderPath = "C:/IT/mysql";
        //创建上传文件目录
        File folder = new File(folderPath);
        //如果文件夹不存在创建对应的文件夹
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //设置文件名
        String fileName = "三号机系统表结构总览xx.xlsx";
        String savePath = folderPath + File.separator + fileName;
        // System.out.println(savePath);

        OutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(savePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回文件保存全路径
        return savePath;
    }

    public static void main(String[] args) {
        try {
            List<String> list2 = outputExcel1.getDatabases();
            System.out.println(list2.size());
            List<List<List<String>>> lists = outputExcel1.getListFromAis(list2);
//            String savepath = outputExcel1.outPutExcel(lists);
//            System.out.println(savepath);
//            outputExcel.getTableNameList("aisdb");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}