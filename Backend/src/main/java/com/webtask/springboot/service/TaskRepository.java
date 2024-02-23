package com.webtask.springboot.service;

import com.webtask.springboot.domain.Task;
import com.webtask.springboot.domain.User;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();

    void saveAll(List<Task> tasks);

    void add(Task task);

    void remove(Task task);

    List<Task> findByUser(User user);
}
