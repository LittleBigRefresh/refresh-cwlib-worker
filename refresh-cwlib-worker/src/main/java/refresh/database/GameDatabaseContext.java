package refresh.database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class GameDatabaseContext implements AutoCloseable {
    private final Connection conn;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public GameDatabaseContext() throws SQLException {
        String url = "jdbc:postgresql://localhost/refresh";
        this.conn = DriverManager.getConnection(url, "refresh", "refresh");
    }

    private void deleteWorkers() throws SQLException {
        String sql = "DELETE FROM \"Workers\" WHERE \"Class\" = 1";
        try(Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public int createWorker() throws SQLException {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        this.deleteWorkers();

        String sql =
                """
                INSERT INTO "Workers" ("Class", "CreatedAt", "LastContact")
                VALUES (?, ?, ?)
                RETURNING "WorkerId"
                """;

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setTimestamp(2, now);
            stmt.setTimestamp(3, now);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Failed to insert worker");
                }
            }
        }
    }

    @Override
    public void close() throws SQLException {
        if(this.conn != null)
            this.conn.close();
    }
}
