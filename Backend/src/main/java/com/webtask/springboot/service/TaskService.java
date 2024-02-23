package com.webtask.springboot.service;

import com.webtask.springboot.domain.Task;
import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.StringTasksDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public TaskService(TaskRepository TaskRepository) {
        this.taskRepository = TaskRepository;
    }

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public void saveTasks(List<Task> tasks){
        taskRepository.saveAll(tasks);
    }

    public void removeTask(Task task){
        taskRepository.remove(task);
    }

    public void newTask(String taskName){
        List<Task> tasks = getTasks();
        Task newTask = new Task(taskName);
        for(Task t: tasks){
            if(t.getTask().equals(newTask.getTask())){
                removeTask(t);
                return;
            }
        }
        taskRepository.add(newTask);
    }

    public void newTask(Task newTask){
        List<Task> tasks = getTasks();
        for(Task t: tasks){
            if(t.getTask().equals(newTask.getTask())){
                removeTask(t);
                return;
            }
        }
        System.out.println("Adding task: " + newTask.toString());
        taskRepository.add(newTask);
    }

    public List<Task> findByUser(User user){
        return taskRepository.findByUser(user);
    }

    public void newTask(String taskName, User user){
        List<Task> tasks = findByUser(user);
        Task newTask = new Task(taskName, user);
        for(Task t: tasks){
            if(t.getTask().equals(newTask.getTask())){
                removeTask(t);
                return;
            }
        }
        taskRepository.add(newTask);
    }


    public StringTasksDto getStringTasks(List<Task> tasks) {
        List<String> tasksString = new ArrayList<>();
        for (Task t : tasks){
            tasksString.add(t.getTask());
        }
        StringTasksDto tasksDto = new StringTasksDto();
        tasksDto.setTasks(tasksString);
        return tasksDto;
    }
}
