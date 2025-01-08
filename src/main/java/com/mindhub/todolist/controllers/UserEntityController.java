package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.ApiResponseDTO;
import com.mindhub.todolist.dtos.UserEntityDTO.*;
import com.mindhub.todolist.exceptions.ApplicationException;
import com.mindhub.todolist.services.IUserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userEntity")
public class UserEntityController {

    @Autowired
    private IUserEntityService userEntityService;

    @PostMapping("/create")
    @Operation(summary = "Se crea un userEntity")
    public ResponseEntity<UserEntityResponseDTO> createUserEntity(@Valid @RequestBody UserEntityRequestDTO userEntityRequestDTO) {
        try{
            UserEntityResponseDTO userEntityResponseDTO = userEntityService.saveUserEntity(userEntityRequestDTO);
            return new ResponseEntity<>(userEntityResponseDTO, HttpStatus.CREATED);
        } catch (ApplicationException e) {
            throw new ApplicationException(" Ha ocurrido un error " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    @Operation(summary = "Trae todos los usuarios de la base de datos")
    public ResponseEntity<ApiResponseDTO<UserEntityResponseDTO>> getAllUsuarios() {
        try {
            List<UserEntityResponseDTO> userEntityResponseDTO = userEntityService.getAll();
            if (userEntityResponseDTO.isEmpty()) {
                return new ResponseEntity<>(new ApiResponseDTO<>(false, "No Hay Usuarios Registrados", userEntityResponseDTO), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponseDTO<>(true, "Usuarios Registrados", userEntityResponseDTO), HttpStatus.OK);
            }
        } catch (ApplicationException e) {
            throw new ApplicationException(" Ha ocurrido un error " + e.getMessage());
        }
    }

    @PatchMapping("/update")
    @Operation(summary = "Se actualiza un usuario en particular")
    public ResponseEntity<ApiResponseDTO<UserEntityResponseDTO>> updateUserEntity(@Valid @RequestBody UserEntityUpdateDTO userEntityUpdateDTO) {
        UserEntityResponseDTO userEntityResponseDTO = userEntityService.update(userEntityUpdateDTO);
        String message = "Usuario Actualizado";
        return new ResponseEntity<>(new ApiResponseDTO<>(true, message, userEntityResponseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Se elimina un usuario en particular")
    public ResponseEntity<?> deleteEntity(@PathVariable Long id) {
        userEntityService.delete(id);
        String message = "Usuario Eliminado exitosamente";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/findUserTasks/{id}")
    public ResponseEntity<ApiResponseDTO<UserEntityTasksResponseDTO>> findUserTasksById(@PathVariable Long id) {
        UserEntityTasksResponseDTO userTasks = userEntityService.userTasks(id);
        String message = "Usuario y Tareas encontradas";
        return new ResponseEntity<>(new ApiResponseDTO<>(true, message, userTasks), HttpStatus.OK);
    }

    @GetMapping("/existUserEntity/{id}")
    @Operation(summary = "Comprueba si un usuario existe en la base de datos")
    public ResponseEntity<?> existUserEntity(@PathVariable Long id) {
        if(userEntityService.existById(id)) {
            String message = "El usuario existe en la base de datos";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            String message = "El usuario no existe en la base de datos";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
