package be.ucll.Taskmanager.DB;

import be.ucll.Taskmanager.DTO.TaskDTO;
import be.ucll.Taskmanager.Domain.SubTask;
import be.ucll.Taskmanager.Domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SubTaskRepository subTaskRepository;

    public Task get(long id){
        Optional<Task> optional = taskRepository.findById(id);
        return optional.orElseThrow(() -> new DbException("no task with "+ id +" found"));
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public List<SubTask> getAllSubtasks(){
       return null;
    }

    public void add(TaskDTO dto){
        taskRepository.save(toEntity(dto));
    }

    public void delete(long id){
        taskRepository.deleteById(id);
        subTaskRepository.removeAllByTaskid(id);
    }

    public void update(TaskDTO dto){
        taskRepository.updateTask(dto.getName(),dto.getDeadline(),dto.getDescription(),dto.isCompleted());
    }

    public Task toEntity(TaskDTO dto){
        Task t = new Task();
        t.setId(dto.getId());
        t.setName(dto.getName());
        t.setDescription(dto.getDescription());
        t.setDeadline(dto.getDeadline());
        t.setSubtasks(dto.getSubtasks());
        return t;
    }
}
