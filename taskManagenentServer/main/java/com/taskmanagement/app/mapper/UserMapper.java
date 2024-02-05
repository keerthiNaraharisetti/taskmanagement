package com.taskmanagement.app.mapper;

import java.util.List;
import org.mapstruct.*;
import com.taskmanagement.app.request.UserRequest;
import com.taskmanagement.app.dto.UserDTO;
import com.taskmanagement.app.model.User;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper{

    UserDTO map(User user);

    List<UserDTO> map (List<User> user);

    User map(UserRequest userRequest);

    void merge(UserRequest userRequest, @MappingTarget User user);
}