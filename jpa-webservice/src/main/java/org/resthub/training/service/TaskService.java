package org.resthub.training.service;

import org.resthub.common.service.CrudService;
import org.resthub.training.model.Task;
import org.resthub.training.model.User;

public interface TaskService extends CrudService<Task, Long> {

    Task affectTaskToUser(Long taskId, Long userId);
    Task findByTitle(String title);

}
