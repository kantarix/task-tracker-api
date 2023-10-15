package org.kantarix.task_tracker_api.api.factories;

import org.kantarix.task_tracker_api.api.dto.TaskStateDto;
import org.kantarix.task_tracker_api.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDtoFactory {

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {
        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ordinal(entity.getOrdinal())
                .build();
    }

}
