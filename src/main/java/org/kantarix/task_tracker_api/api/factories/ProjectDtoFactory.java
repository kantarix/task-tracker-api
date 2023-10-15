package org.kantarix.task_tracker_api.api.factories;

import org.kantarix.task_tracker_api.api.dto.ProjectDto;
import org.kantarix.task_tracker_api.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity entity) {
        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }

}
