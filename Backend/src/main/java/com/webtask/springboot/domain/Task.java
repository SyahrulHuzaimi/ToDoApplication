package com.webtask.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tasks")
public final class Task {

    @Id
    @GeneratedValue
    private Integer id;
    private String task;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                '}';
    }
}
