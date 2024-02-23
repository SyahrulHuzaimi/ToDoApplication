package com.webtask.springboot.repository;

import com.webtask.springboot.domain.Task;
import com.webtask.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DbTaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAll();

    List<Task> findByUser(User user);

}
