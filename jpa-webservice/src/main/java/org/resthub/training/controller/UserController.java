package org.resthub.training.controller;

import org.resthub.training.model.Task;
import org.resthub.training.model.User;
import org.resthub.training.repository.TaskRepository;
import org.resthub.training.repository.UserRepository;
import org.resthub.training.service.TaskService;
import org.resthub.web.controller.RepositoryBasedRestController;
import org.resthub.web.controller.ServiceBasedRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Controller
@RequestMapping(value = "/api/user")
public class UserController extends RepositoryBasedRestController<User, Long, UserRepository> {

    @Inject
    @Named("userRepository")
    @Override
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long getIdFromResource(User resource) {
        return resource.getId();
    }
}
