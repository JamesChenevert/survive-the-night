package com.example.survivethenight;

import java.sql.*;
import java.util.*;

public class JDBCDao {

    private final String URL = "jdbc:sqlite:game.db";

    public JDBCDao() {
        createTable();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS inventory (item TEXT PRIMARY KEY)";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SAVE ITEM
    public void saveItem(String item) {
        String sql = "INSERT OR IGNORE INTO inventory(item) VALUES(?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // LOAD ALL ITEMS
    public List<String> loadInventory() {
        List<String> items = new ArrayList<>();
        String sql = "SELECT item FROM inventory";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                items.add(rs.getString("item"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}