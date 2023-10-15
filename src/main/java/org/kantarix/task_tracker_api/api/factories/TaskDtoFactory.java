package org.kantarix.task_tracker_api.api.factories;

import org.kantarix.task_tracker_api.api.dto.TaskDto;
import org.kantarix.task_tracker_api.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity entity) {
        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .ordinal(entity.getOrdinal())
                .createdAt(entity.getCreatedAt())
                .build();
    }

}
