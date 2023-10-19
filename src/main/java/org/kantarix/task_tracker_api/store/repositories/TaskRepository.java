package org.kantarix.task_tracker_api.store.repositories;

import org.kantarix.task_tracker_api.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findById(Long taskId);

    // TODO: by user id
    Stream<TaskEntity> streamAllBy();

}
