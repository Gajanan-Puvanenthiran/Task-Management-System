package com.gajanan.Task_SpringBoot.controller.admin;

import com.gajanan.Task_SpringBoot.dto.CommentDTO;
import com.gajanan.Task_SpringBoot.dto.TaskDTO;
import com.gajanan.Task_SpringBoot.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        TaskDTO createdTaskDTO =adminService.createTask(taskDTO);
        if(createdTaskDTO == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(adminService.getAllTasks());
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        adminService.deleteTask(id);
        return ResponseEntity.ok(null);
    }



    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        TaskDTO updatedTask=adminService.updateTask(id,taskDTO);
        if(updatedTask == null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/tasks/search/{title}")
    public ResponseEntity<List<TaskDTO>> searchTask(@PathVariable String title){
        return ResponseEntity.ok(adminService.searchTaskByTitle(title));

    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getTaskById(id));
    }

    @PostMapping("/task/comment/{taskId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long taskId, @RequestParam String content){
        CommentDTO createdCommentDTO =adminService.createComment(taskId,content);
        if(createdCommentDTO == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDTO);
    }

    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentDTO>> getCommentByTaskId(@PathVariable Long taskId){
        return ResponseEntity.ok(adminService.getCommentByTaskId(taskId));
    }
}
