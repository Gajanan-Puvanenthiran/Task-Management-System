package com.gajanan.Task_SpringBoot.services.employee;

import com.gajanan.Task_SpringBoot.dto.CommentDTO;
import com.gajanan.Task_SpringBoot.dto.TaskDTO;

import java.util.List;

public interface EmployeeService {

    List<TaskDTO> getTasksByUserId();

    TaskDTO updateTask(Long id, String status);

    TaskDTO getTaskById(Long id);

    CommentDTO createComment(Long taskId, String content);

    List<CommentDTO> getCommentByTaskId(Long taskId);
}
