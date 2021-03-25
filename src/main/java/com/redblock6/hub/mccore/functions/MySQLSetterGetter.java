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
            statement.setInt(1, (getDust(uuid) + amount));
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

    public void updateEXP(UUID uuid, int amount) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET EXP=? WHERE UUID=?");
            statement.setInt(1, (getEXP(uuid) + amount));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void resetEXP(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET EXP=? WHERE UUID=?");
            statement.setInt(1, 0);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getEXP(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("EXP");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateEXPMax(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET EXPMAX=? WHERE UUID=?");
            statement.setInt(1, (getEXPMax(uuid) + 100));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getEXPMax(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("EXPMAX");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateLevel(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET LEVEL=? WHERE UUID=?");
            statement.setInt(1, (getLevel(uuid) + 1));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer getLevel(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getInt("LEVEL");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void completedTutorial(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("UPDATE " + pl.global_table + " SET TUTORIAL=? WHERE UUID=?");
            statement.setString(1, "Completed");
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getTutorial(UUID uuid) {
        try {
            PreparedStatement statement = pl.getConnection()
                    .prepareStatement("SELECT * FROM " + pl.global_table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            results = statement.executeQuery();
            results.next();

            return results.getString("TUTORIAL");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
