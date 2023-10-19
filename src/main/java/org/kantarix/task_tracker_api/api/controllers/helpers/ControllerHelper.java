package org.kantarix.task_tracker_api.api.controllers.helpers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kantarix.task_tracker_api.api.exceptions.BadRequestException;
import org.kantarix.task_tracker_api.api.exceptions.NotFoundException;
import org.kantarix.task_tracker_api.store.entities.TaskEntity;
import org.kantarix.task_tracker_api.store.entities.TaskState;
import org.kantarix.task_tracker_api.store.repositories.TaskRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class ControllerHelper {

    TaskRepository taskRepository;

    public TaskEntity getTaskOrThrowException(Long taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Task with id %d doesn't exist.", taskId))
                );
    }

    public TaskState getTaskOrThrowException(String taskState) {
        try{
            return TaskState.valueOf(taskState.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(String.format("Wrong state value %S.", taskState));
        }
    }

}
