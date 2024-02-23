package com.webtask.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue
    private Integer id;
    private String task;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Task(String task) {
        this.id = null;
        this.task = task;
    }
    //@PersistenceCreator
    public Task(Integer id, String task){
        this.id = id;
        this.task = task;
    }

    public Task(Integer id, String task, User user) {
        this.id = id;
        this.task = task;
        this.user = user;
    }

    public Task(String task, User user) {
        this.task = task;
        this.user = user;
    }

    public Task() {

    }

    public String getTask() {
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return Objects.equals(task, task1.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                '}';
    }
}
