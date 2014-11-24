package org.resthub.training.controller;

import org.fest.assertions.api.Assertions;
import org.resthub.test.AbstractWebTest;
import org.resthub.training.model.Task;
import org.testng.annotations.Test;

public class TaskControllerTest extends AbstractWebTest {

    public TaskControllerTest() {
        // Activate resthub-web-server and resthub-jpa Spring profiles
        super("resthub-web-server,resthub-jpa,resthub-pool-bonecp");
    }


    @Test
    public void testFindByTitle() {
        this.request("api/task").xmlPost(new Task("task1"));
        this.request("api/task").xmlPost(new Task("task2"));
        Task task1 = this.request("api/task/title/task1").jsonGet().resource(Task.class);
        Assertions.assertThat(task1).isNotNull();
        Assertions.assertThat(task1.getTitle()).isEqualTo("task1");
    }
}
