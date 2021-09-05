package de.bydopeman.dopelib;

import java.sql.*;

public class MySQL {

    public static String HOST;
    public static String USER;
    public static String DATABASE;
    public static String PASSWORD;
    public static String PORT;

    public static Connection con;

    public static void connect(){
        if(!isConnected()){
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USER, PASSWORD);
                System.out.println("[MySQL] Server Economy Connected");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void disconnect(){
        if(isConnected()){
            try {
                con.close();
                System.out.println("[MySQL] Server Economy Disconnected");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() { return  (con != null); }

    public static void createTable(){
        try {
            con.prepareStatement("CREATE TABLE IF NOT EXISTS coins (UUID TEXT, COINS BIGINT")
                    .executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void update(String qry){
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(qry);
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ResultSet query(String qry){
        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery(qry);
        } catch (SQLException e){
            connect();
            e.printStackTrace();
        }
        return rs;
    }
}
