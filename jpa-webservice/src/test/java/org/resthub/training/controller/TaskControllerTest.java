package org.resthub.training.controller;

import org.fest.assertions.api.Assertions;
import org.resthub.test.AbstractWebTest;
import org.resthub.training.model.Task;
import org.resthub.training.model.User;
import org.testng.annotations.Test;

public class TaskControllerTest extends AbstractWebTest {

    public TaskControllerTest() {
        // Activate resthub-web-server and resthub-jpa Spring profiles
        super("resthub-web-server,resthub-jpa");
    }


    @Test
    public void testFindByTitle() {
        this.request("api/task").xmlPost(new Task("newTask1"));
        this.request("api/task").xmlPost(new Task("task2"));
        Task task1 = this.request("api/task/title/newTask1").jsonGet().resource(Task.class);
        Assertions.assertThat(task1).isNotNull();
        Assertions.assertThat(task1.getTitle()).isEqualTo("newTask1");
    }

    @Test
    public void testAffectTaskToUser() {
        Task task = this.request("api/task").xmlPost(new Task("new Task")).resource(Task.class);
        User user = this.request("api/user").xmlPost(new User("new User")).resource(User.class);
        String responseBody = this.request("api/task/" + task.getId() + "/user/" + user.getId()).put("").getBody();
        Assertions.assertThat(responseBody).isNotEmpty();
        Assertions.assertThat(responseBody).contains("new Task");
        Assertions.assertThat(responseBody).contains("new User");
    }
}
