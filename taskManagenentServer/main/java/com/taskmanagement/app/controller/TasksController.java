package com.taskmanagement.app.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.taskmanagement.app.model.Task;
import com.taskmanagement.app.request.TaskRequest;
import com.taskmanagement.app.dto.TaskDTO;
import com.taskmanagement.app.exception.ResourceNotFoundException;
import com.taskmanagement.app.mapper.TaskMapper;
import com.taskmanagement.app.service.TaskService;
import com.taskmanagement.app.specification.TaskSpecificationsBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping(path = "/tasks")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class TasksController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    
    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        log.info("Request /tasks/" + id);
        Task task = taskService.find(id);
        return taskMapper.map(task);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<TaskDTO> index(Pageable pageable) {
        log.info("Request /tasks/");
        Page<Task> tasks = taskService.findAll(pageable);
        List<TaskDTO> dtos = taskMapper.map(tasks.getContent());
        return new PageImpl<>(dtos, pageable, tasks.getTotalElements());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public Page<TaskDTO> filterTasks(
        @RequestParam(value = "search") String search,
        @RequestParam(name = "page", defaultValue = "0") int pageNumber,
        @RequestParam(name = "size", defaultValue = "10") int pageSize){
            log.info("Request /tasks/filter", search, pageNumber, pageSize);
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            TaskSpecificationsBuilder builder = new TaskSpecificationsBuilder(search);
            Specification<Task> spec =  builder.build();
            Page<Task> tasks = taskService.findAll(spec, pageable);
            List<TaskDTO> dtos = taskMapper.map(tasks.getContent());
            return new PageImpl<>(dtos, pageable, tasks.getTotalElements());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody TaskRequest request) throws Exception {
        log.info("Post Request /tasks/ data" + request);
        Task task = taskMapper.map(request);
        task = taskService.create(task);
        return taskMapper.map(task);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDTO update(@PathVariable(value = "id") Long id, @RequestBody TaskRequest request) throws ResourceNotFoundException {
        log.info("Put Request /tasks/" + id + " data" + request);
        Task task = taskService.update(request, id);
        return taskMapper.map(task);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void destroy(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        log.info("Delete /tasks/" + id);
        taskService.destroy(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/user/{id}")
    public List<TaskDTO> findByTasksCreatedBy(@PathVariable(value = "id") Long userId) throws Exception {
        log.info("Request /tasks/user/" + userId);
        List<Task> task = taskService.findByTasksCreatedBy(userId);
        return taskMapper.map(task);
    }
}