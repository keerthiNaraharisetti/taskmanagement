package com.taskmanagement.app.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import com.taskmanagement.app.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, Long> {

    Page<User> findAll(Specification<User> specification, Pageable pageable);

    User findByUsername(String username);

    Optional<User> findById(Long id);
}
