package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.UserEntityDTO.*;

import java.util.List;

public interface IUserEntityService {
    UserEntityResponseDTO saveUserEntity(UserEntityRequestDTO userEntityRequestDTO);
    List<UserEntityResponseDTO> getAll();
    UserEntityResponseDTO update(UserEntityUpdateDTO userEntityUpdateDTO);
    void delete(Long id);
    UserEntityTasksResponseDTO userTasks(Long id);
    boolean existById(Long id);
}