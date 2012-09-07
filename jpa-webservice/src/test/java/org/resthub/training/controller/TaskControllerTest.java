package org.resthub.training.controller;

import org.fest.assertions.api.Assertions;
import org.resthub.test.common.AbstractWebTest;
import org.resthub.training.model.Task;
import org.resthub.training.model.User;
import org.resthub.web.Client;
import org.resthub.web.Response;
import org.resthub.web.XmlHelper;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TaskControllerTest extends AbstractWebTest {

    protected String rootUrl() {
        return "http://localhost:9797/api/task";
    }

    protected String userRootUrl() {
        return "http://localhost:9797/api/user";
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

    @Test
    public void testAffectTaskToUser() throws IllegalArgumentException, InterruptedException, ExecutionException, IOException {
        Client httpClient = new Client();
        Response resp = httpClient.url(rootUrl()).xmlPost(new Task("task1")).get();
        Task task = XmlHelper.deserialize(resp.getBody(), Task.class);
        resp = httpClient.url(userRootUrl()).xmlPost(new User("user1")).get();
        User user = XmlHelper.deserialize(resp.getBody(), User.class);
        String responseBody = httpClient.url(rootUrl() + "/" + task.getId() + "/user/" + user.getId()).put("").get().getBody();
        Assertions.assertThat(responseBody).isNotEmpty();
        Assertions.assertThat(responseBody).contains("task1");
        Assertions.assertThat(responseBody).contains("user1");
    }
}
