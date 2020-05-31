package be.ucll.Taskmanager.Domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "Name cannot be emtpy")
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "deadline cannot be empty")
    private LocalDateTime deadline;
    @NotNull(message = "description cannot be empty")
    @NotEmpty(message = "description cannot be empty")
    private String description;
    private boolean completed = false;
    @Transient
    private List<SubTask> subtasks;

    public Task(String name, String description,LocalDateTime deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        subtasks = new ArrayList<>();
    }

    public Task(){
        subtasks = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public String deadlineToString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return deadline.format(formatter);
    }

    public void setId(long id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void addSubTask(SubTask t){
        long id = 1;
        if (this.subtasks.size()>0){
            id = this.subtasks.get(subtasks.size()-1).getId()+1;
        }
        t.setId(id);
        this.subtasks.add(t);
    }

    public List<SubTask> getSubtasks(){
        return this.subtasks;
    }

    public boolean isCompleted(){
        return this.completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public void setSubtasks(List<SubTask> subtasks){
        this.subtasks = subtasks;
    }
}
