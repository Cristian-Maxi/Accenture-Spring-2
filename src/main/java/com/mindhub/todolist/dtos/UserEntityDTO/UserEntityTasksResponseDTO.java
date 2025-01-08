package com.mindhub.todolist.dtos.UserEntityDTO;

import com.mindhub.todolist.dtos.TaskEntityDTO.TaskEntityResponseDTO;

import java.util.List;

public record UserEntityTasksResponseDTO(
        Long id,
        String username,
        List<TaskEntityResponseDTO>tasks
) {
}
