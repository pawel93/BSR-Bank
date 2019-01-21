package bsr.util;

import bsr.model.History;
import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;
import java.util.ArrayList;



public class DBUtil
{

    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static Connection conn = null;
    private static final String url = "jdbc:sqlite:database/accounts.db";

    public static void dbConnect()
    {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void dbDisconnect()
    {
        try {
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDB()
    {
        Statement statement = null;
        try {
            dbConnect();
            statement = conn.createStatement();
            statement.executeUpdate("drop table if exists accounts");
            statement.executeUpdate("CREATE TABLE accounts(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT, login TEXT, password TEXT)");

            statement.executeUpdate("drop table if exists bills");
            statement.executeUpdate("CREATE TABLE bills(" +
                    "id INTEGER, number TEXT, saldo DOUBLE)");

            statement.executeUpdate("drop table if exists history");
            statement.executeUpdate("CREATE TABLE history(" +
                    "id INTEGER, account TEXT, title TEXT, income DOUBLE, outcome DOUBLE, source TEXT, saldo DOUBLE, date DATE )");

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
                try {
                    if(statement != null)
                        statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            dbDisconnect();
        }
    }


    public static ResultSet dbExecuteQuery(String queryStmt)
    {
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;

        try {
            dbConnect();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(queryStmt);
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            dbDisconnect();
        }

        return crs;
    }

    public static void dbExecuteTransaction(String Stmt1, String Stmt2){

        Statement statement = null;

        try {
            dbConnect();
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(Stmt1);
            statement.executeUpdate(Stmt2);
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            if(statement != null){
                try {
                    statement.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            dbDisconnect();
        }



    }

    public static void dbExecuteUpdate(String queryStmt)
    {
        Statement statement = null;

        try {
            dbConnect();
            statement = conn.createStatement();
            statement.executeUpdate(queryStmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(statement != null){
                try {
                    statement.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            dbDisconnect();
        }

    }



}
