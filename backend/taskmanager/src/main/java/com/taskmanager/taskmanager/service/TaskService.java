package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Add Task
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // Get All Tasks
    public List<Task> getAllTasks() {

        return taskRepository.findAll();
    }
    // Update Task
    public Task updateTask(Long id, Task updatedTask) {

        Task task = taskRepository.findById(id).orElse(null);

        if(task != null) {

            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setPriority(updatedTask.getPriority());
            task.setStatus(updatedTask.getStatus());

            return taskRepository.save(task);
        }

        return null;
    }
    // Delete Task
    public void deleteTask(Long id) {

        taskRepository.deleteById(id);
    }
}