package org.kantarix.task_tracker_api.store.repositories;

import org.kantarix.task_tracker_api.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {



}
