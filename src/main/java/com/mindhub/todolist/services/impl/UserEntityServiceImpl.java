package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.dtos.TaskEntityDTO.TaskEntityResponseDTO;
import com.mindhub.todolist.dtos.UserEntityDTO.*;
import com.mindhub.todolist.exceptions.ApplicationException;
import com.mindhub.todolist.models.TaskEntity;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositorys.ITaskEntityRepository;
import com.mindhub.todolist.repositorys.IUserEntityRepository;
import com.mindhub.todolist.services.IUserEntityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements IUserEntityService {

    @Autowired
    private IUserEntityRepository userEntityRepository;

    @Autowired
    private ITaskEntityRepository taskEntityRepository;

    @Override
    @Transactional
    public UserEntityResponseDTO saveUserEntity(UserEntityRequestDTO userEntityRequestDTO) {
        if (userEntityRepository.existsByEmail(userEntityRequestDTO.email())) {
            throw new ApplicationException("email", "El email ya existe en la base de datos");
        }
        UserEntity userEntity = new UserEntity(
                userEntityRequestDTO.username(),
                userEntityRequestDTO.password(),
                userEntityRequestDTO.email()
        );
        userEntityRepository.save(userEntity);
        UserEntityResponseDTO userEntityResponseDTO = new UserEntityResponseDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail()
        );
        return userEntityResponseDTO;
    }

    @Override
    public List<UserEntityResponseDTO> getAll() {
        List<UserEntity> userEntities = userEntityRepository.findAll();
        List<UserEntityResponseDTO> userEntityResponseDTOS = userEntities.stream()
                .map(userEntity -> new UserEntityResponseDTO(
                        userEntity.getId(),
                        userEntity.getUsername(),
                        userEntity.getEmail())).collect(Collectors.toList());
        return userEntityResponseDTOS;
    }

    @Override
    @Transactional
    public UserEntityResponseDTO update(UserEntityUpdateDTO userEntityUpdateDTO) {
        UserEntity userEntity = userEntityRepository.findById(userEntityUpdateDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("El ID del usuario no encontrado"));
        if (userEntityUpdateDTO.username() != null && !userEntityUpdateDTO.username().isBlank()) {
            userEntity.setUsername(userEntityUpdateDTO.username());
        }
        if (userEntityUpdateDTO.password() != null && !userEntityUpdateDTO.password().isBlank()) {
            userEntity.setPassword(userEntityUpdateDTO.password());
        }
        if (userEntityUpdateDTO.email() != null && !userEntityUpdateDTO.email().isBlank()) {
            userEntity.setEmail(userEntityUpdateDTO.email());
        }
        userEntityRepository.save(userEntity);
        return new UserEntityResponseDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail());
    }

    @Override
    public void delete(Long id) {
        UserEntity userEntity = userEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el ID del usuario ingresado"));
        userEntityRepository.delete(userEntity);
    }

    @Override
    public UserEntityTasksResponseDTO userTasks(Long id) {
        UserEntity userEntity = userEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el ID del usuario ingresado"));
        List<TaskEntity> taskEntity = taskEntityRepository.findByUserEntityId(id);
        if (taskEntity.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron tareas para el usuario con ID: " + id);
        }
        UserEntityTasksResponseDTO userTaks = new UserEntityTasksResponseDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                taskEntity.stream().map(task -> new TaskEntityResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus())).collect(Collectors.toList()));
        return userTaks;
    }

    @Override
    public boolean existById(Long id) {
        return userEntityRepository.existsById(id);
    }
}