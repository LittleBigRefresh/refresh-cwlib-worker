package refresh.database.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class WorkerInfo {
    public int WorkerId;
    public WorkerClass Class;
    public Timestamp CreatedAt;
    public Timestamp LastContact;

    public WorkerInfo(ResultSet rs) throws SQLException {
        this.WorkerId = rs.getInt(1);
        this.Class = WorkerClass.values()[rs.getInt(2)];
        this.CreatedAt = rs.getTimestamp(3);
        this.LastContact = rs.getTimestamp(4);
    }
}
