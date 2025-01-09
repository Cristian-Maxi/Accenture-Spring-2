package com.mindhub.todolist.mappers;

import com.mindhub.todolist.dtos.TaskEntityDTO.TaskEntityResponseDTO;
import com.mindhub.todolist.models.TaskEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TaskEntityMapper {
    public static TaskEntityResponseDTO toTaskResponseDTO(TaskEntity taskEntity) {
        return new TaskEntityResponseDTO(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getStatus()
        );
    }

    public static List<TaskEntityResponseDTO> toTaskResponseDTOList(List<TaskEntity> taskEntities) {
        return taskEntities.stream()
                .map(TaskEntityMapper::toTaskResponseDTO)
                .collect(Collectors.toList());
    }
}
