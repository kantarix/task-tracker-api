package org.kantarix.task_tracker_api.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kantarix.task_tracker_api.api.dto.ProjectDto;
import org.kantarix.task_tracker_api.api.exceptions.BadRequestException;
import org.kantarix.task_tracker_api.api.factories.ProjectDtoFactory;
import org.kantarix.task_tracker_api.store.entities.ProjectEntity;
import org.kantarix.task_tracker_api.store.repositories.ProjectRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {

    ProjectRepository projectRepository;
    ProjectDtoFactory projectDtoFactory;

    public static final String CREATE_PROJECT = "/api/projects";

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {
        projectRepository
                .findByName(name)
                .ifPresent(object -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists", name));
                });

        ProjectEntity project = projectRepository.save(
                ProjectEntity.builder()
                        .name(name)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(project);
    }

}
