package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.auth.JwtUtil;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // ADD TASK
    @PostMapping
    public Task addTask(
            @RequestBody Task task,
            @RequestHeader("Authorization") String token
    ) {

        token = token.substring(7);

        String email =
                JwtUtil.extractEmail(token);

        User user =
                userRepository.findByEmail(email);

        task.setUser(user);

        return taskRepository.save(task);
    }

    // GET USER TASKS
    @GetMapping
    public List<Task> getTasks(
            @RequestHeader("Authorization") String token
    ) {

        System.out.println("RAW TOKEN = " + token);

        token = token.substring(7);

        System.out.println("AFTER SUBSTRING = " + token);

        String email = JwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email);

        return taskRepository.findByUser(user);
    }// UPDATE TASK
    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task updatedTask
    ) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task Not Found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());

        return taskRepository.save(task);
    }
    // DELETE TASK
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {

        taskRepository.deleteById(id);

        return "Task Deleted";
    }
}