package com.adi3000.code.synch.redminetrello.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractLogger {

    private final Logger logger = LogManager.getLogger(this.getClass());

    protected Logger getLogger() {
        return logger;
    }

}
