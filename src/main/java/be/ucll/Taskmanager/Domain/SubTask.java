package be.ucll.Taskmanager.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubTask {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Task task;

    @NotNull(message = "Name cannot be emtpy")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "description cannot be empty")
    @NotEmpty(message = "description cannot be empty")
    private String description;

    public SubTask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SubTask(){}

    public void setName(String name) {
        this.name = name;
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

    public void setId(long id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String toString(){
        return this.name;
    }

    public void setTask(Task task){
        this.task = task;
    }

    public Task getTask(){
        return this.task;
    }
}
