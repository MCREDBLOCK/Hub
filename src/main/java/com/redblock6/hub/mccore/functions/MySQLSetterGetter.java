package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLSetterGetter {

    Main pl = Main.getInstance();

    public boolean playerExistsGlobal(UUID uuid) {
        try {
            PreparedStatement global_statement = pl.getConnection().prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            global_statement.setString(1, uuid.toString());

            ResultSet results = global_statement.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(final UUID uuid, Player p) {
        try {
            PreparedStatement global_statement = pl.getConnection().prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            global_statement.setString(1, uuid.toString());
            ResultSet results = global_statement.executeQuery();
            results.next();

            if (!playerExistsGlobal(uuid)) {
                PreparedStatement insert = pl.getConnection().prepareStatement("INSERT INTO " + pl.global_table + " (UUID,NAME,MAGICDUST,LEVEL,EXP,EXPMAX,TUTORIAL) VALUES (?,?,?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, p.getName());
                insert.setInt(3, 0);
                insert.setInt(4, 1);
                insert.setInt(5, 0);
                insert.setInt(6, 100);
                insert.setString(7, "Incomplete");

                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDust(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET MAGICDUST=? WHERE UUID=?");
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet results;

    public Integer getDust(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("MAGICDUST");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
