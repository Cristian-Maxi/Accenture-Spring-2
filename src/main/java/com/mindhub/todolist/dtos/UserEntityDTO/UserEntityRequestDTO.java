package com.mindhub.todolist.dtos.UserEntityDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserEntityRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank @Email
        String email
) {
}
