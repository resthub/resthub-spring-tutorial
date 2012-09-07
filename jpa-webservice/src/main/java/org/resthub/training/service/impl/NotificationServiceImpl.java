package org.resthub.training.service.impl;

import org.resthub.training.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named("notificationService")
public class NotificationServiceImpl implements NotificationService {

    protected static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Override
    public void send(String email, String message) {
        logger.info("New message for " + email + ": " + message);
    }
}
