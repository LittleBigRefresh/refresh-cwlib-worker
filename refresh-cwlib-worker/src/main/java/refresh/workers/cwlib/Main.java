package refresh.workers.cwlib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import refresh.workers.WorkerManager;

import java.sql.SQLException;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws SQLException {
        // setup logging stuffs
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        logger.info("Starting up worker manager...");

        logger.info("Initializing configs...");
        // todo

        logger.info("Starting worker manager!");

        WorkerManager manager = new WorkerManager();
        manager.start();

        try {
            manager.waitForExit();
        } catch (InterruptedException e) {
            logger.warn("Interrupted, exiting...");
            System.exit(0);
        }
    }
}