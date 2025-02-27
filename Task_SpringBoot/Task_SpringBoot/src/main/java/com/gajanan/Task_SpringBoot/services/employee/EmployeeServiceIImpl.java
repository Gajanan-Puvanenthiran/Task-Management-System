package com.gajanan.Task_SpringBoot.services.employee;

import com.gajanan.Task_SpringBoot.dto.CommentDTO;
import com.gajanan.Task_SpringBoot.dto.TaskDTO;
import com.gajanan.Task_SpringBoot.entities.Comment;
import com.gajanan.Task_SpringBoot.entities.Task;
import com.gajanan.Task_SpringBoot.entities.User;
import com.gajanan.Task_SpringBoot.enums.TaskStatus;
import com.gajanan.Task_SpringBoot.repositories.CommentRepository;
import com.gajanan.Task_SpringBoot.repositories.TaskRepository;
import com.gajanan.Task_SpringBoot.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceIImpl implements EmployeeService{

    private final TaskRepository taskRepository;

    private final JwtUtil jwtUtil;

    private final CommentRepository commentRepository;

    @Override
    public List<TaskDTO> getTasksByUserId() {
        User user=jwtUtil.getLoggedInUser();
        if(user != null){
            return taskRepository.findAllByUserId(user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task:: getTaskDTO)
                    .collect(Collectors.toList());

        }
        throw  new EntityNotFoundException("User not found");
    }

    @Override
    public TaskDTO updateTask(Long id, String status) {
        Optional<Task> optionalTask=taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task existingTask=optionalTask.get();
            existingTask.setTaskStatus(mapStringToTaskStatus(status));
            return taskRepository.save(existingTask).getTaskDTO();
        }
        throw new EntityNotFoundException("Task not found");
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Optional<Task> optionalTask= taskRepository.findById(id);
        return optionalTask.map(Task:: getTaskDTO).orElse(null);
    }

    @Override
    public CommentDTO createComment(Long taskId, String content) {
        Optional<Task> optionalTask= taskRepository.findById(taskId);
        User user=jwtUtil.getLoggedInUser();
        if(optionalTask.isPresent() && user !=null){
            Comment comment=new Comment();
            comment.setCreatedAt(new Date());
            comment.setContent(content);
            comment.setTask(optionalTask.get());
            comment.setUser(user);
            return commentRepository.save(comment).getCommentDTO();
        }
        throw new EntityNotFoundException("User or Task not found");
    }

    @Override
    public List<CommentDTO> getCommentByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId)
                .stream()
                .map(Comment::getCommentDTO)
                .collect(Collectors.toList());
    }

    private TaskStatus mapStringToTaskStatus(String status){
        return switch (status){
            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;

            default -> TaskStatus.CANCELLED;

        };
    }
}
