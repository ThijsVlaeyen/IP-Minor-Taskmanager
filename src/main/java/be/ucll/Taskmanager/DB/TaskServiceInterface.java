package be.ucll.Taskmanager.DB;

import be.ucll.Taskmanager.Domain.Task;

import java.time.LocalDateTime;
import java.util.List;


public interface TaskServiceInterface {
    List<Task> getTasks();
    void addTask(Task task);

    void removeTask(long id);
    Task get(long id);

    void addTask(String naam, String description, LocalDateTime deadline);

    void update(long id, Task t);
}
