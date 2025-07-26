package refresh.database.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentJobState {
    public String JobId;
    public WorkerClass Class;
    public String State;

    public PersistentJobState(ResultSet rs) throws SQLException {
        this.JobId = rs.getString(1);
        this.Class = WorkerClass.values()[rs.getInt(2)];
        this.State = rs.getString(3);
    }
}
