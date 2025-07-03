package com.example.todo.controller;

import com.example.todo.model.User;
import com.example.todo.service.TaskService;
import com.example.todo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    private final TaskService service;
    private final UserRepository userRepository;

    public TaskController(TaskService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) Long userId, Model model) {
        model.addAttribute("users", userRepository.findAll());

        if (userId != null) {
            model.addAttribute("tasks", service.getTasksByUserId(userId));
            model.addAttribute("selectedUserId", userId);
        } else {
            model.addAttribute("tasks", java.util.List.of());
        }

        return "index";
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String description, @RequestParam Long userId) {
        service.addTask(description, userId);
        return "redirect:/?userId=" + userId;
    }

    @PostMapping("/user")
    public String addUser(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, @RequestParam Long userId) {
        service.deleteTask(id);
        return "redirect:/?userId=" + userId;
    }
}
