package com.taskmanagement.app.mapper;

import java.util.List;
import org.mapstruct.*;
import com.taskmanagement.app.request.TaskRequest;
import com.taskmanagement.app.dto.TaskDTO;
import com.taskmanagement.app.model.Task;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TaskMapper{

    TaskDTO map(Task task);

    List<TaskDTO> map (List<Task> task);

    Task map(TaskRequest taskRequest);

    void merge(TaskRequest taskRequest, @MappingTarget Task task);
}