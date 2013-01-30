package org.resthub.training.repository;

import org.resthub.training.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByTitle(String name);

}
