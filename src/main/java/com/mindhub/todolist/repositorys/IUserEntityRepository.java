package com.mindhub.todolist.repositorys;

import com.mindhub.todolist.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserEntityRepository extends JpaRepository<UserEntity, Long> {
}
