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
    public String addTask(@RequestBody CreateTaskRequest request) {
        Task task = new Task(
                request.getName(),
                request.getDescription(),
                request.getDefaultStatus(),
                request.getTimeToComplete());

        boolean success = taskManager.addTask(task);
        return success ? "Task created" : "Task not created";
    }

    @PostMapping("/{id}/update/time")
    public String updateTimeToComplete(@PathVariable("id") UUID id, @RequestBody UpdateTimeRequest request) {
        UpdateTimeRequest request_ = new UpdateTimeRequest(id, request.getDuration());
        boolean success = taskManager.updateTimeToComplete(request_);
        return success ? "Time updated successfully" : "Time not updated";
    }

    @DeleteMapping("/{id}/delete")
    public String removeTask(@PathVariable("id") UUID id) {
        boolean success = taskManager.removeTask(id);
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
