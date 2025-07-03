package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.model.User;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;
    private final UserRepository userRepository;

    public TaskService(TaskRepository repo, UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public List<Task> getTasksByUserId(Long userId) {
        return repo.findAll().stream()
                .filter(task -> task.getUser().getId().equals(userId))
                .toList();
    }

    public void addTask(String description, Long userId) {
        Task task = new Task();
        task.setDescription(description);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        task.setUser(user);

        repo.save(task);
    }

    public void deleteTask(Long id) {
        repo.deleteById(id);
    }
}
