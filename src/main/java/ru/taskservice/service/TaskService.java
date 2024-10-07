package ru.taskservice.service;

import org.springframework.stereotype.Service;
import ru.taskservice.model.Task;
import ru.taskservice.model.UpdateTimeRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {
    private final Map<String, Task> tasks;

    public TaskService() {
        tasks = new HashMap<>();
    }

    public boolean addTask(Task task) {
        String key = String.valueOf(task.getId());
        if (tasks.containsKey(key)) {
            return false;
        }
        tasks.put(key, task);
        return true;
    }

    public boolean removeTask(Task task) {
        return tasks.remove(task.getId().toString()) != null;
    }

    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    public Collection<Task> findTasks(String keyword) {
        Collection<Task> tasks_ = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
                tasks_.add(task);
            }
        }
        return tasks_;
    }

    public boolean updateTimeToComplete(UpdateTimeRequest request) {
        Task task = tasks.get(request.getId().toString());
        if (task != null && task.getTimeToComplete().compareTo(request.getDuration()) >= 0) {
            task.setTimeToComplete(task.getTimeToComplete().minus(request.getDuration()));
            return true;
        }
        return false;
    }
}
