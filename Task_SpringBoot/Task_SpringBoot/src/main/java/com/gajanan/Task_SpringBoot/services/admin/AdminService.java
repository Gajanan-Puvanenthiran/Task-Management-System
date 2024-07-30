package com.gajanan.Task_SpringBoot.services.admin;

import com.gajanan.Task_SpringBoot.dto.CommentDTO;
import com.gajanan.Task_SpringBoot.dto.TaskDTO;
import com.gajanan.Task_SpringBoot.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getUsers();

    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> getAllTasks();

    void deleteTask(Long id);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    List<TaskDTO> searchTaskByTitle(String title);

    TaskDTO getTaskById(Long id);

    CommentDTO createComment(Long taskId, String content);

    List<CommentDTO> getCommentByTaskId(Long taskId);

}
