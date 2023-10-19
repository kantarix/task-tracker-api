package org.kantarix.task_tracker_api.store.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.config.Task;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    @Builder.Default
    String description = "";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    TaskState state = TaskState.TODO;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    Instant updatedAt = Instant.now();

}
