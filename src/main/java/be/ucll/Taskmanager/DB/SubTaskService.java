package be.ucll.Taskmanager.DB;

import be.ucll.Taskmanager.DTO.SubTaskDTO;
import be.ucll.Taskmanager.Domain.SubTask;
import be.ucll.Taskmanager.Domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.ucll.Taskmanager.Domain.DTOFormatter.DTOToSubtask;
import static be.ucll.Taskmanager.Domain.DTOFormatter.createDTOfromSubtask;

@Service
public class SubTaskService {
    SubTaskRepository repository;

    @Autowired
    public SubTaskService(SubTaskRepository repository){
        this.repository = repository;
    }

    public SubTaskDTO add(SubTaskDTO dto){
        repository.save(DTOToSubtask(dto));
        return dto;
    }

    public void delete(long id){
        repository.deleteById(id);
    }

    public void update(SubTaskDTO dto){
        repository.update(dto.getName(),dto.getDescription(),dto.getTask(),dto.getId());
    }

    public List<SubTaskDTO> getAll(Task task){
        List<SubTaskDTO> result = new ArrayList<>();
        for (SubTask t : repository.findByTask(task)){
            t.setTask(null);
            result.add(createDTOfromSubtask(t));
        }
        return result;
    }

    public List<SubTask> getAll(){
        return repository.findAll();
    }

    public SubTaskDTO get(long  id){
        Optional<SubTask> optionalSubTask = repository.findById(id);
        return createDTOfromSubtask(optionalSubTask.orElseThrow(() -> new DbException("subtask not found")));
    }
}
