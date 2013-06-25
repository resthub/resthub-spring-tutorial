package org.resthub.training.controller;

import org.resthub.training.model.Task;
import org.resthub.training.repository.TaskRepository;
import org.resthub.web.controller.RepositoryBasedRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.inject.Named;

@Controller
@RequestMapping(value = "/api/task")
public class TaskController extends RepositoryBasedRestController<Task, Long, TaskRepository> {

    @Inject
    @Named("taskRepository")
    @Override
    public void setRepository(TaskRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "title/{title}", method = RequestMethod.GET)
    @ResponseBody
    public Task findByTitle(@PathVariable String title) {
        return this.repository.findByTitle(title);
    }
}
