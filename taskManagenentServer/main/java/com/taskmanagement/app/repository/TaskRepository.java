package com.taskmanagement.app.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.taskmanagement.app.model.Task;

@Repository
@Transactional
public interface TaskRepository extends JpaSpecificationExecutor<Task>, JpaRepository<Task, Long> {

    Page<Task> findAll(Specification<Task> specification, Pageable pageable);

    Optional<Task> findById(Long id);

    Optional<Task> findByIdAndCreatedBy(Long id, Long createdBy);

    List<Task> findByCreatedBy(Long userId);
}
