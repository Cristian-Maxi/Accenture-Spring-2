package com.mindhub.todolist.mappers;

import com.mindhub.todolist.dtos.UserEntityDTO.UserEntityResponseDTO;
import com.mindhub.todolist.models.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserEntityMapper {
    public static UserEntityResponseDTO toUserResponseDTO(UserEntity userEntity) {
        return new UserEntityResponseDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail()
        );
    }

    public static List<UserEntityResponseDTO> toUserResponseDTOList(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(UserEntityMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }
}