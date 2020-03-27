package be.ucll.Taskmanager.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SubTaskDTO {
    private long id;
    private long taskid;
    @NotNull(message = "Name cannot be emtpy")
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "description cannot be empty")
    @NotEmpty(message = "description cannot be empty")
    private String description;
    private boolean completed = false;

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

    public boolean isCompleted(){
        return this.completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public long getTaskid(){return this.taskid;}

    public void setTaskid(long taskid){this.taskid = taskid;}

}
