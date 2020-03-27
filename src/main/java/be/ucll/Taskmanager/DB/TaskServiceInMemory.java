package be.ucll.Taskmanager.DB;

import be.ucll.Taskmanager.Domain.SubTask;
import be.ucll.Taskmanager.Domain.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service("memory")
public class TaskServiceInMemory implements TaskServiceInterface {
    private Map<Long,Task> tasks;

    public TaskServiceInMemory(){
//        this.tasks = new HashMap<>();
//        this.addTask("task1", "test task", LocalDateTime.now());
//        this.addTask("task2","test taks 2", LocalDateTime.now());
//        this.addTask("task3","test taks 3", LocalDateTime.now());
//        this.tasks.get(1).addSubTask(new SubTask("subtask 1/1","subtask of main task 1"));
//        this.tasks.get(1).addSubTask(new SubTask("subtask 1/2","subtask of main task 1"));
//        this.tasks.get(1).addSubTask(new SubTask("subtask 1/3","subtask of main task 1"));
//        this.tasks.get(2).addSubTask(new SubTask("subtask 2/1","subtask of main task 1"));
//        this.tasks.get(3).addSubTask(new SubTask("subtask 3/1","subtask of main task 1"));
    }

    public void addTask(Task task){
        List<Task> tasks = getTasks();
        long id = 1;
        if (tasks.size()>0) {
            id = tasks.get(tasks.size() - 1).getId() + 1;
        }
        task.setId(id);
        this.tasks.put(id,task);
        this.tasks.put(task.getId(),task);
    }

    @Override
    public void removeTask(long id) {
        this.tasks.remove(id);
    }

    @Override
    public Task get(long id) {
       return this.tasks.get(id);
    }

    @Override
    public void addTask(String naam, String description, LocalDateTime deadline) {
        List<Task> tasks = getTasks();
        long id = 1;
        if (tasks.size()>0) {
            id = tasks.get(tasks.size() - 1).getId() + 1;
        }
        Task t = new Task(naam,description,deadline);
        t.setId(id);
        this.tasks.put(id,t);
    }

    public List<Task> getTasks(){
        List<Task> tasklist = new ArrayList<>();
        for (Map.Entry<Long,Task> task:this.tasks.entrySet()) {
            tasklist.add(task.getValue());
        }
        return tasklist;
    }

    public void update(long id,Task t){
        tasks.replace(id,t);
    }
}
