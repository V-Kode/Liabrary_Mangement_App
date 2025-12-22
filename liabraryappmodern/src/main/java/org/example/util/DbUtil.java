package org.example.util;

import java.sql.*;

public final class DbUtil {

    private static final String URL = "jdbc:sqlite:library.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DbUtil() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initDatabase() {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {

            st.execute("""
                CREATE TABLE IF NOT EXISTS books(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL,
                    copies INTEGER DEFAULT 1,
                    issued INTEGER DEFAULT 0
                )
            """);

            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS c FROM books");
            if (rs.next() && rs.getInt("c") == 0) {
                st.execute("INSERT INTO books(title, author, copies, issued) VALUES ('Intro to Java', 'John Doe', 5, 0)");
                st.execute("INSERT INTO books(title, author, copies, issued) VALUES ('DS in Java', 'Jane Smith', 3, 0)");
                st.execute("INSERT INTO books(title, author, copies, issued) VALUES ('Algo for Java', 'Alex Lee', 7, 0)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
