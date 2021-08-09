package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.task.TaskRepository;
import com.bdular.inventorytracker.data.task.data.Task;
import com.bdular.inventorytracker.data.task.data.TaskPriority;
import com.bdular.inventorytracker.data.task.data.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static com.bdular.inventorytracker.data.task.data.TaskPriority.*;
import static com.bdular.inventorytracker.data.task.data.TaskStatus.*;

@Service
@Validated
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<Task> getTasksFromUser(@NotNull String id) {
        return taskRepository.findAllByUploader_IDOrderByPriorityDesc(id);
    }

    public List<Task> getTopPriorityTasksFromUser(@NotNull String id) {
        return taskRepository.findFirst5ByUploader_IDAndPriorityGreaterThanAndStatusEqualsOrderByPriorityDescSubmittedDesc(id, HIGH, CREATED);
    }

    public List<Task> getTopPriorityTasks() {
        return taskRepository.findFirst5ByPriorityGreaterThanAndStatusEqualsOrderByPriorityDescSubmittedDesc(HIGH, CREATED);
    }

    public Task getTask(@NotNull String id) {
        return taskRepository.findById(id).orElseThrow();
    }

    public void addNewTask(@Valid Task task) {
        task.setSubmitted(LocalDateTime.now());
        taskRepository.insert(task);
    }

    @Transactional
    public void changeTaskPriority(@NotNull String id, int priority) {
        TaskPriority taskPriority;
        switch (priority) {
            case 1:
                taskPriority = MEDIUM;
                break;
            case 2:
                taskPriority = HIGH;
                break;
            case 3:
                taskPriority = URGENT;
                break;
            default:
                taskPriority = LOW;
                break;
        }
        taskRepository.findById(id).map(task -> {
            task.setPriority(taskPriority);
            return taskRepository.save(task);
        });
    }

    @Transactional
    public void changeTaskStatus(@NotNull String id, int status) {
        TaskStatus taskStatus;
        if (status == 1) {
            taskStatus = ON_HOLD;
        } else {
            taskStatus = IN_PROGRESS;
        }
        taskRepository.findById(id).map(task -> {
            task.setStatus(taskStatus);
            return taskRepository.save(task);
        });
    }

    @Transactional
    public void finishTask(@NotNull String id) {
        taskRepository.findById(id).map(task -> {
            task.setFinished(LocalDateTime.now());
            task.setStatus(FINISHED);
            return taskRepository.save(task);
        }).orElseThrow();
    }

    public void deleteTask(@NotNull String id) {
        taskRepository.deleteById(id);
    }

}
