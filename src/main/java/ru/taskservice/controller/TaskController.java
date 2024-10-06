package ru.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.taskservice.model.CreateTaskRequest;
import ru.taskservice.model.Task;
import ru.taskservice.service.TaskService;
import ru.taskservice.model.UpdateTimeRequest;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskManager;

    @PostMapping("/create")
    public Task addTask(@RequestBody CreateTaskRequest request) {
        Task task = new Task(
                UUID.randomUUID(),
                request.getName(),
                request.getDescription(),
                request.getTimeToComplete());

        boolean success = taskManager.addTask(task);
        return success ? task : null;
    }

    @PostMapping("/update/time")
    public String updateTimeToComplete(@RequestBody UpdateTimeRequest request) {
        boolean success = taskManager.updateTimeToComplete(request);
        return success ? "Time updated successfully" : "Time not updated";
    }

    @DeleteMapping
    public String removeTask(@RequestBody Task task) {
        boolean success = taskManager.removeTask(task);
        return success ? "Task removed successfully" : "Task not found";
    }

    @GetMapping
    public Collection<Task> getAllTasks() {
        return taskManager.getAllTasks();
    }

    @GetMapping("/search")
    public Collection<Task> findTasks(@RequestParam("keyword") String keyword) {
        return taskManager.findTasks(keyword);
    }
}
