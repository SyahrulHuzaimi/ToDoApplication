package com.webtask.springboot.repository;

import com.webtask.springboot.domain.Task;
import com.webtask.springboot.domain.User;
import com.webtask.springboot.service.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final DbTaskRepository repo;

    public TaskRepositoryImpl(DbTaskRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Task> findAll() {
        return repo.findAll();
    }

    @Override
    public void saveAll(List<Task> tasks) {

    }


    @Override
    public void add(Task task) {
        repo.save(task);
    }

    @Override
    public void remove(Task task) {
        repo.delete(task);
    }

    @Override
    public List<Task> findByUser(User user) {
        return repo.findByUser(user);
    }
}
