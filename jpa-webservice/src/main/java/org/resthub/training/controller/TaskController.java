package org.resthub.training.controller;

import org.resthub.training.model.Task;
import org.resthub.training.repository.TaskRepository;
import org.resthub.web.controller.RepositoryBasedRestController;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Controller
@RequestMapping(value = "/api/task")
public class TaskController extends RepositoryBasedRestController<Task, Long, TaskRepository> {

    @Inject
    @Named("taskRepository")
    @Override
    public void setRepository(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long getIdFromResource(Task resource) {
        return resource.getId();
    }

    @RequestMapping(method = RequestMethod.GET, params = "page=no")
    @ResponseBody
    public List<Task> findAllNonPaginated() {
        return this.repository.findAll();
    }
}
