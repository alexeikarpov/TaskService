package ru.taskservice.service;

import org.springframework.stereotype.Service;
import ru.taskservice.model.Task;
import ru.taskservice.model.UpdateTimeRequest;
import ru.taskservice.repository.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
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

            stmt.setObject(1, id);
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
                task.setId((UUID) rs.getObject("id"));
                task.setName(rs.getString("name"));
                task.setDescription(rs.getString("description"));
                task.setTimeToCompleteSeconds(rs.getLong("time_to_complete"));
                task.setTimeToComplete(Duration.ofSeconds(rs.getLong("time_to_complete")));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Collection<Task> findTasks(String keyword) {
        String sql = "SELECT id, name, description, time_to_complete FROM tasks WHERE LOWER(name) LIKE ?";
        Collection<Task> tasks = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword.toLowerCase() + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task();
                    task.setId((UUID) rs.getObject("id"));
                    task.setName(rs.getString("name"));
                    task.setDescription(rs.getString("description"));
                    task.setTimeToCompleteSeconds(rs.getLong("time_to_complete"));
                    task.setTimeToComplete(Duration.ofSeconds(rs.getLong("time_to_complete")));

                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    public boolean updateTimeToComplete(UpdateTimeRequest request) {
        String selectSql = "SELECT time_to_complete FROM tasks WHERE id = ?";
        String updateSql = "UPDATE tasks SET time_to_complete = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            long currentTimeToComplete;

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setObject(1, request.getId());

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        currentTimeToComplete = rs.getLong("time_to_complete");
                    } else {
                        return false;
                    }
                }
            }
            long updatedTimeToComplete = currentTimeToComplete - request.getDuration().getSeconds();

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setLong(1, updatedTimeToComplete);
                updateStmt.setObject(2, request.getId());
                int rowsAffected = updateStmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
