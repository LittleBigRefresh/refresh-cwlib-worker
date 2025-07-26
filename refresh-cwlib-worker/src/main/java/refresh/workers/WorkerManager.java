package refresh.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import refresh.database.GameDatabaseContext;

import java.sql.SQLException;

public class WorkerManager {
    private static final Logger logger = LogManager.getLogger();

    private final int _workerId;

    private Thread _thread = null;
    private boolean _threadShouldRun = false;

    private long _lastContactUpdate = System.currentTimeMillis();

    public WorkerManager() throws SQLException {
        try(GameDatabaseContext context = new GameDatabaseContext()) {
            this._workerId = context.createWorker();
            logger.debug("Assigned worker id: {}", this._workerId);
        }
    }

    private void runWorkCycle() throws Exception {
        try(WorkContext context = new WorkContext()) {
            long now = System.currentTimeMillis();
            if(now - this._lastContactUpdate < 5000) return;

            this._lastContactUpdate = now;
            boolean updated = context.Database.markWorkerContacted(this._workerId);

            if(!updated) {
                logger.info("Worker is shutting down as we've been replaced.");
                this.stop(false);
            }
        }
    }

    public void start() {
        logger.debug("Starting the worker thread");
        this._threadShouldRun = true;

        Thread thread = new Thread(() -> {
            while(this._threadShouldRun) {
                try {
                    Thread.sleep(500);
                    this.runWorkCycle();
                }
                catch(Exception e) {
                    logger.fatal("Critical exception while running work cycle: {}", String.valueOf(e));
                    logger.fatal("Waiting for 1 second before trying to run another cycle.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        thread.start();

        this._thread = thread;
    }

    public void stop(boolean join) throws InterruptedException {
        if(this._thread == null) return;
        logger.debug("Stopping the worker thread");

        this._threadShouldRun = false;
        if(join)
            waitForExit();
    }

    public void waitForExit() throws InterruptedException {
        this._thread.join();
    }
}
