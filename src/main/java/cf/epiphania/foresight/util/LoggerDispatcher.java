package cf.epiphania.foresight.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerDispatcher {
    protected final Logger logger;
    protected LoggerDispatcher() {
        this.logger = LogManager.getLogger(this.getClass());
    }
}
