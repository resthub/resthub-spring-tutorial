package org.resthub.training;

import org.resthub.training.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@ImportResource("classpath*:applicationContext.xml")
@Profile("test")
public class MocksConfiguration {

    @Bean(name = "notificationService")
    public NotificationService mockedNotificationService() {
        return mock(NotificationService.class);
    }

}