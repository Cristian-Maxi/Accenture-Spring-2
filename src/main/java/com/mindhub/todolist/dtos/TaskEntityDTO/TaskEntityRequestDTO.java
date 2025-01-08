package com.mindhub.todolist.dtos.TaskEntityDTO;

import com.mindhub.todolist.enums.Status;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskEntityRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @Enumerated
        Status status,
        @NotNull
        Long usuarioId
) {
}