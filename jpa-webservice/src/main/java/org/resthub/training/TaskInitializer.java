package org.resthub.training;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.common.util.PostInitialize;

import org.resthub.training.model.Task;
import org.resthub.training.repository.TaskRepository;

@Named("taskInitializer")
public class TaskInitializer {

    @Inject
    @Named("taskRepository")
    private TaskRepository taskRepository;

    @PostInitialize
    public void init() {
        taskRepository.save(new Task("testTask1"));
        taskRepository.save(new Task("testTask2"));
        taskRepository.save(new Task("testTask3"));
    }
}
