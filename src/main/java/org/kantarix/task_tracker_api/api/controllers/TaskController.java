package org.kantarix.task_tracker_api.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kantarix.task_tracker_api.api.controllers.helpers.ControllerHelper;
import org.kantarix.task_tracker_api.api.dto.AskDto;
import org.kantarix.task_tracker_api.api.dto.TaskDto;
import org.kantarix.task_tracker_api.api.exceptions.BadRequestException;
import org.kantarix.task_tracker_api.api.factories.TaskDtoFactory;
import org.kantarix.task_tracker_api.store.entities.TaskEntity;
import org.kantarix.task_tracker_api.store.entities.TaskState;
import org.kantarix.task_tracker_api.store.repositories.TaskRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TaskController {

    TaskRepository taskRepository;

    TaskDtoFactory taskDtoFactory;

    ControllerHelper controllerHelper;

    public static final String FETCH_TASKS = "/tasks";
    public static final String DELETE_TASK = "/tasks/{task_id}";
    public static final String CREATE_OR_UPDATE_TASK = "/tasks";

    @Transactional(readOnly = true)
    @GetMapping(FETCH_TASKS)
    public List<TaskDto> fetchTasks(
            // TODO: get user for getting his tasks
    ) {
        return taskRepository.streamAllBy()
                .map(taskDtoFactory::makeTaskDto)
                .collect(Collectors.toList());
    }

    // TODO: get user id for set to task
    @PutMapping(CREATE_OR_UPDATE_TASK)
    public TaskDto createOrUpdateTask(
            @RequestParam(value = "task_id", required = false) Optional<Long> optionalTaskId,
            @RequestParam(value = "name") Optional<String> optionalTaskName,
            @RequestParam(value = "description") Optional<String> optionalTaskDescription,
            @RequestParam(value = "state") Optional<String> optionalTaskState
    ) {
        boolean isCreate = !optionalTaskId.isPresent();

        optionalTaskName = optionalTaskName.filter(taskName -> !taskName.trim().isEmpty());
        optionalTaskDescription = optionalTaskDescription.filter(taskDescription -> !taskDescription.trim().isEmpty());

        if (isCreate && !optionalTaskName.isPresent()) {
            throw new BadRequestException("Task name can't be empty");
        }

        final TaskEntity task = optionalTaskId
                .map(controllerHelper::getTaskOrThrowException)
                .orElseGet(() -> TaskEntity.builder().build());

        optionalTaskName.ifPresent(task::setName);
        optionalTaskDescription.ifPresent(task::setDescription);
        optionalTaskState.ifPresent(taskState ->
                task.setState(controllerHelper.getTaskOrThrowException(taskState))
        );
        task.setUpdatedAt(Instant.now());

        final TaskEntity savedTask = taskRepository.saveAndFlush(task);

        return taskDtoFactory.makeTaskDto(savedTask);
    }

    @DeleteMapping(DELETE_TASK)
    public AskDto deleteTask(
            @PathVariable(name = "task_id") Long taskId
    ) {
        controllerHelper.getTaskOrThrowException(taskId);

        taskRepository.deleteById(taskId);

        return AskDto.makeDefault(true);
    }

}
