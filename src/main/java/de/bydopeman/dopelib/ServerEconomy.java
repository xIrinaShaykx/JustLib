package de.bydopeman.dopelib;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerEconomy {

    public static boolean playerExists(String uuid){
        try {
            ResultSet rs = MySQL.query("SELECT * FROM coins WHERE UUID= '" + uuid + "'");
            if(rs.next()){
                return (rs.getString("UUID") != null);
            }
            return false;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void createPlayer(String uuid){
        MySQL.update("INSERT INTO coins(UUID, COINS) VALUES ('" + uuid + "', '0');");
    }

    public static void setCoins(String uuid, int amount){
        if(playerExists(uuid)){
            MySQL.update("UPDATE coins SET COINS= '" + amount + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setCoins(uuid, amount);
        }
    }

    public static int getCoins(String uuid){
        if(playerExists(uuid)){
            try {
                PreparedStatement ps = MySQL.con.prepareStatement("SELECT COINS FROM coins WHERE UUID= '" + uuid + "';");
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    return rs.getInt("COINS");
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            getCoins(uuid);
        }
        return 0;
    }

    public static void removeCoins(String uuid, int amount){
        if(playerExists(uuid)){
            int coins = getCoins(uuid) - amount;
            MySQL.update("UPDATE coins SET COINS= '" + coins + "' WHERE UUID= '" + uuid + "';");
        }
    }

    public static void addCoins(String uuid, int amount){
        if(playerExists(uuid)){
            int coins = getCoins(uuid) + amount;
            MySQL.update("UPDATE coins SET COINS= '" + coins + "' WHERE UUID= '" + uuid + "';");
        }
    }

    public static void resetCoins(String uuid){
        if(playerExists(uuid)){
            MySQL.update("UPDATE coins SET COINS= '0' WHERE UUID= '" + uuid + "';");
        }
    }

}
