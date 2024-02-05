package com.taskmanagement.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.taskmanagement.app.exception.ResourceNotFoundException;
import com.taskmanagement.app.mapper.TaskMapper;
import com.taskmanagement.app.model.Task;
import com.taskmanagement.app.model.User;
import com.taskmanagement.app.repository.TaskRepository;
import com.taskmanagement.app.request.TaskRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;
    
    public Task find(Long id) throws ResourceNotFoundException {
        User currentUser = userService.getCurrentUser();
        if(currentUser.hasRole("ADMIN")){
            return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task is not found with id: " + id));
        }else{
            return taskRepository.findByIdAndCreatedBy(id, currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Your task with id: " + id + " is not found"));
        }
    }

    public Page<Task> findAll(Specification<Task> specification, Pageable pageable) {
        return taskRepository.findAll(specification, pageable);
    }
    
    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task update(@NonNull TaskRequest taskRequest, Long id) throws ResourceNotFoundException {
        User currentUser = userService.getCurrentUser();
        Task task = find(id);
        taskMapper.merge(taskRequest, task);
        task.setUpdatedBy(currentUser.getId());
        return taskRepository.save(task);
    }
    
    public Task create(@NonNull Task task) {
        User currentUser = userService.getCurrentUser();
        task.setCreatedBy(currentUser.getId());
        return taskRepository.save(task);
    }

    public void destroy(Long id) throws ResourceNotFoundException {
        Task task = find(id);
        taskRepository.delete(task);
    }

    public List<Task> findByTasksCreatedBy(Long userId){
        return taskRepository.findByCreatedBy(userId);
    }
    
}
