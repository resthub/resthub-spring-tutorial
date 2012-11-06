package org.resthub.training.controller;

import org.fest.assertions.api.Assertions;
import org.resthub.test.AbstractWebTest;
import org.resthub.training.model.Task;
import org.resthub.training.model.User;
import org.resthub.web.Response;
import org.resthub.web.XmlHelper;
import org.testng.annotations.Test;

public class TaskControllerTest extends AbstractWebTest {

    public TaskControllerTest() {
        // Activate resthub-web-server and resthub-jpa Spring profiles
        super("resthub-web-server,resthub-jpa");


    @Test
    public void testFindByName() {
        this.request("api/task").xmlPost(new Task("task1"));
        this.request("api/task").xmlPost(new Task("task2"));
        Task task1 = this.request("api/task/name/task1").getJson().resource(Task.class);
        Assertions.assertThat(task1).isNotNull();
        Assertions.assertThat(task1.getName()).isEqualTo("task1");
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
