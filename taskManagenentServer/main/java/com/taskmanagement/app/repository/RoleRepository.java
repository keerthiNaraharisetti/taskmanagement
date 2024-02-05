package com.taskmanagement.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanagement.app.model.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
