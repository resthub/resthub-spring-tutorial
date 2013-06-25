package org.resthub.training;

import org.resthub.common.util.PostInitialize;
import org.resthub.training.model.Task;
import org.resthub.training.model.User;
import org.resthub.training.repository.TaskRepository;
import org.resthub.training.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Named("taskInitializer")
public class TaskInitializer {

    @Inject
    @Named("taskRepository")
    private TaskRepository taskRepository;

    @Inject
    @Named("userRepository")
    private UserRepository userRepository;

    @PostInitialize
    @Transactional(readOnly = false)
    public void init() {
        User user1 = new User("testUser1", "user1@test.org");
        user1 = userRepository.save(user1);
        User user2 = userRepository.save(new User("testUser2", "user2@test.org"));
        taskRepository.save(new Task("testTask1", user1, "bla bla"));
        taskRepository.save(new Task("testTask2", user1, "bla bla"));
        taskRepository.save(new Task("testTask3", user2, "bla bla"));
        taskRepository.save(new Task("testTask4", "bla bla"));
    }
}
