package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.dtos.TaskEntityDTO.*;
import com.mindhub.todolist.mappers.TaskEntityMapper;
import com.mindhub.todolist.models.TaskEntity;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.ITaskEntityRepository;
import com.mindhub.todolist.repositories.IUserEntityRepository;
import com.mindhub.todolist.services.ITaskEntityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskEntityServiceImpl implements ITaskEntityService {

    @Autowired
    private ITaskEntityRepository taskEntityRepository;

    @Autowired
    private IUserEntityRepository userEntityRepository;

    @Override
    public Long countByUserEntityId(Long id) {
        return taskEntityRepository.countByUserEntityId(id);
    }

    @Override
    @Transactional
    public TaskEntityResponseDTO saveTask(TaskEntityRequestDTO taskEntityRequestDTO) {
        UserEntity userEntity = userEntityRepository.findById(taskEntityRequestDTO.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el ID del usuario ingresado"));
        TaskEntity taskEntity = new TaskEntity(
                taskEntityRequestDTO.title(),
                taskEntityRequestDTO.description(),
                taskEntityRequestDTO.status(),
                userEntity);
        taskEntityRepository.save(taskEntity);
        // Sin Mapper
//        TaskEntityResponseDTO taskEntityResponseDTO = new TaskEntityResponseDTO(
//                taskEntity.getId(),
//                taskEntity.getTitle(),
//                taskEntity.getDescription(),
//                taskEntity.getStatus());
//        return taskEntityResponseDTO;
        // Con Mapper
        return TaskEntityMapper.toTaskResponseDTO(taskEntity);
    }

    @Override
    public List<TaskEntityResponseDTO> getAll() {
        List<TaskEntity> taskEntities = taskEntityRepository.findAll();
        // Sin Mapper
//        List<TaskEntityResponseDTO> taskEntityResponseDTOS = taskEntities.stream()
//                .map(taskEntity -> new TaskEntityResponseDTO(
//                        taskEntity.getId(),
//                        taskEntity.getTitle(),
//                        taskEntity.getDescription(),
//                        taskEntity.getStatus())).collect(Collectors.toList());
//        return taskEntityResponseDTOS;
        // Con Mapper
        return TaskEntityMapper.toTaskResponseDTOList(taskEntities);
    }

    @Override
    @Transactional
    public TaskEntityResponseDTO update(TaskEntityUpdateDTO taskEntityUpdateDTO) {
        TaskEntity taskEntity = taskEntityRepository.findById(taskEntityUpdateDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("El ID de la tarea no fue encontrada"));
        if (taskEntityUpdateDTO.title() != null && !taskEntityUpdateDTO.title().isBlank()) {
            taskEntity.setTitle(taskEntityUpdateDTO.title());
        }
        if (taskEntityUpdateDTO.description() != null && !taskEntityUpdateDTO.description().isBlank()) {
            taskEntity.setDescription(taskEntityUpdateDTO.description());
        }
        if (taskEntityUpdateDTO.status() != null) {
            taskEntity.setStatus(taskEntityUpdateDTO.status());
        }
        taskEntityRepository.save(taskEntity);
        // Sin Mapper
//        return new TaskEntityResponseDTO(
//                taskEntity.getId(),
//                taskEntity.getTitle(),
//                taskEntity.getDescription(),
//                taskEntity.getStatus());
        // Con Mapper
        return TaskEntityMapper.toTaskResponseDTO(taskEntity);
    }

    @Override
    public void delete(Long id) {
        TaskEntity taskEntity = taskEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El ID de la tarea no fue encontrada"));
        taskEntityRepository.delete(taskEntity);
    }
}