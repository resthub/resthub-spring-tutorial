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
        User user1 = userRepository.save(new User("testUser1"));
        User user2 = userRepository.save(new User("testUser2"));
        taskRepository.save(new Task("testTask1", user1));
        taskRepository.save(new Task("testTask2", user1));
        taskRepository.save(new Task("testTask3", user2));
        taskRepository.save(new Task("testTask4"));
    }
}
