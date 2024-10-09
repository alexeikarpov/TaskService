package ru.taskservice.service;

import org.springframework.stereotype.Service;
import ru.taskservice.model.Task;
import ru.taskservice.model.UpdateTimeRequest;
import ru.taskservice.repository.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class TaskService {

    public boolean addTask(Task task) {
        String sql = "INSERT INTO tasks (id, name, description, time_to_complete) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, task.getId());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.setLong(4, task.getTimeToCompleteSeconds());
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeTask(UUID id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);  // UUID
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Collection<Task> getAllTasks() {
        String sql = "SELECT id, name, description, time_to_complete FROM tasks";
        Collection<Task> tasks = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Task task = new Task();
                task.setId((UUID) rs.getObject("id"));  // UUID
                task.setName(rs.getString("name"));
                task.setDescription(rs.getString("description"));
                task.setTimeToCompleteSeconds(rs.getLong("time_to_complete"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

//    public Collection<Task> findTasks(String keyword) {
//        Collection<Task> tasks_ = new ArrayList<>();
//        for (Task task : tasks.values()) {
//            if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
//                tasks_.add(task);
//            }
//        }
//        return tasks_;
//    }

    public boolean updateTimeToComplete(UpdateTimeRequest request) {
        String sql = "UPDATE tasks SET time_to_complete = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, request.getDuration().getSeconds());
            stmt.setObject(2, request.getId());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
