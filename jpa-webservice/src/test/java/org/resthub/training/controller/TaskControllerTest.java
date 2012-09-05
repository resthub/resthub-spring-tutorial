package org.resthub.training.controller;

import org.fest.assertions.api.Assertions;
import org.resthub.test.common.AbstractWebTest;
import org.resthub.training.model.Task;
import org.resthub.web.Client;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TaskControllerTest extends AbstractWebTest {

    protected String rootUrl() {
        return "http://localhost:8080/api/task";
    }


    @Test
    public void testCreateResource() throws IllegalArgumentException, InterruptedException, ExecutionException, IOException {
        Client httpClient = new Client();
        httpClient.url(rootUrl()).xmlPost(new Task("task1")).get();
        httpClient.url(rootUrl()).xmlPost(new Task("task2")).get();
        String responseBody = httpClient.url(rootUrl()).setQueryParameter("page", "no")
                .getJson().get().getBody();
        Assertions.assertThat(responseBody).isNotEmpty();
        Assertions.assertThat(responseBody).doesNotContain("\"content\":2");
        Assertions.assertThat(responseBody).contains("task1");
        Assertions.assertThat(responseBody).contains("task2");
    }
}
