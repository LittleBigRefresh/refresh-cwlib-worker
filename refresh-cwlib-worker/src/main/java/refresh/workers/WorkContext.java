package refresh.workers;

import refresh.database.GameDatabaseContext;

import java.sql.SQLException;

public class WorkContext implements AutoCloseable {
    public final GameDatabaseContext Database;

    public WorkContext() throws SQLException {
        this.Database = new GameDatabaseContext();
    }

    @Override
    public void close() throws Exception {
        this.Database.close();
    }
}
