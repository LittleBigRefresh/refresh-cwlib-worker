package refresh.workers.cwlib;

import cwlib.CwlibConfiguration;
import refresh.database.GameDatabaseContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println(CwlibConfiguration.JAR_DIRECTORY.getAbsolutePath());

        try(GameDatabaseContext db = new GameDatabaseContext()) {
            db.createWorker();
        }
    }
}