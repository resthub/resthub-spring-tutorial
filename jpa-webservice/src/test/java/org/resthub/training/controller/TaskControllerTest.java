package org.resthub.training.controller;

import org.fest.assertions.api.Assertions;
import org.resthub.test.AbstractWebTest;
import org.resthub.training.model.Task;
import org.testng.annotations.Test;

public class TaskControllerTest extends AbstractWebTest {

    public TaskControllerTest() {
        // Activate resthub-web-server and resthub-jpa Spring profiles
        super("resthub-web-server,resthub-jpa");
    }


    @Test
    public void testFindByName() {
        this.request("api/task").xmlPost(new Task("task1"));
        this.request("api/task").xmlPost(new Task("task2"));
        Task task1 = this.request("api/task/name/task1").getJson().resource(Task.class);
        Assertions.assertThat(task1).isNotNull();
        Assertions.assertThat(task1.getName()).isEqualTo("task1");
    }
}
