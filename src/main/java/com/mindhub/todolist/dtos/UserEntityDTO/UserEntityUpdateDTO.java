package com.mindhub.todolist.dtos.UserEntityDTO;

import jakarta.validation.constraints.NotNull;

public record UserEntityUpdateDTO(
        @NotNull
        Long id,
        String username,
        String password,
        String email
) {
}
