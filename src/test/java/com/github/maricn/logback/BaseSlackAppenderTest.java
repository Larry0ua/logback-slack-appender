package com.github.maricn.logback;

import ch.qos.logback.core.Context;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nikola on 2017-02-03.
 *
 * @author nikola
 */
@Slf4j
public class BaseSlackAppenderTest {

    protected Logger createLogger(
            String token, String channel, String type, String loggerName, Integer drainTimeout, boolean addHostname,
            String additionalFields) {

        log.info("Creating logger {}. token={}, type={}, drainTimeout={}, addHostname={}, additionalFields={}",
                loggerName, token, type, drainTimeout, addHostname, additionalFields);

        ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);
        Context logbackContext = logbackLogger.getLoggerContext();
        SlackAppender slackAppender = new SlackAppender();
        slackAppender.setContext(logbackContext);
        slackAppender.setToken(token);
        slackAppender.setChannel(channel);
        slackAppender.setDebug(true);
        slackAppender.setLogzioUrl("http://" + mockListener.getHost() + ":" + mockListener.getPort());
        slackAppender.setAddHostname(addHostname);
        if (drainTimeout != null) {
            slackAppender.setDrainTimeoutSec(drainTimeout);
        }
        if (additionalFields != null) {
            slackAppender.setAdditionalFields(additionalFields);
        }
        slackAppender.start();
        assertThat(slackAppender.isStarted()).isTrue();
        logbackLogger.addAppender(slackAppender);
        logbackLogger.setAdditive(false);
        return logbackLogger;
    }
}
