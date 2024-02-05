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
import com.taskmanagement.app.model.User;
import com.taskmanagement.app.request.UserRequest;
import com.taskmanagement.app.dto.UserDTO;
import com.taskmanagement.app.mapper.UserMapper;
import com.taskmanagement.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.context.annotation.Lazy;
import com.taskmanagement.app.exception.ResourceNotFoundException;
import com.taskmanagement.app.exception.BadRequestException;

@RestController
@RequestMapping(path = "/users")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class UsersController {

    private final UserService userService;
    private final UserMapper userMapper;
    
    @GetMapping(path = "/{id}")
    public UserDTO show(@PathVariable(value = "id") Long id) throws ResourceNotFoundException, BadRequestException {
        log.info("Request /users/" + id);
        User user = userService.find(id);
        return userMapper.map(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<UserDTO> index(Pageable pageable) {
        log.info("Request /users/");
        Page<User> users = userService.findAll(pageable);
        List<UserDTO> dtos = userMapper.map(users.getContent());
        return new PageImpl<>(dtos, pageable, users.getTotalElements());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserRequest request) throws Exception {
        log.info("Post Request /users/ data" + request);
        User user = userMapper.map(request);
        user = userService.create(user);
        return userMapper.map(user);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO update(@PathVariable(value = "id") Long id, @RequestBody UserRequest request) throws ResourceNotFoundException, BadRequestException {
        log.info("Put Request /users/" + id + " data" + request);
        User user = userService.update(request, id);
        return userMapper.map(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void destroy(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        log.info("Delete /users/" + id);
        userService.destroy(id);
    }

}