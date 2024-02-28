package com.webtask.springboot.controller;

import com.webtask.springboot.domain.Task;
import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.StringTasksDto;
import com.webtask.springboot.dto.TaskDto;
import com.webtask.springboot.security.UserPrincipal;
import com.webtask.springboot.service.AuthService;
import com.webtask.springboot.service.TaskService;
import com.webtask.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final AuthService authService;
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/tasks")
    public StringTasksDto showTasks(@AuthenticationPrincipal UserPrincipal userPrincipal){
        Optional<User> optUser = userService.findUserByUsername(userPrincipal.getUsername());
        User user = optUser.get();
        List<Task> tasks= taskService.findByUser(user);
        return taskService.getStringTasks(tasks);
    }

    @PostMapping("/tasks")
    public StringTasksDto newTask(@RequestBody @Valid TaskDto taskDto, @AuthenticationPrincipal UserPrincipal userPrincipal){
        Optional<User> optUser = userService.findUserByUsername(userPrincipal.getUsername());
        User user = optUser.get();
        taskService.newTask(taskDto.getTask(), user);
        List<Task> tasks= taskService.findByUser(user);
        return taskService.getStringTasks(tasks);
    }
}
