package be.ucll.Taskmanager.controller;

import be.ucll.Taskmanager.DB.SubTaskService;
import be.ucll.Taskmanager.DB.TaskService;
import be.ucll.Taskmanager.DTO.SubTaskDTO;
import be.ucll.Taskmanager.Domain.DTOFormatter;
import be.ucll.Taskmanager.Domain.SubTask;
import be.ucll.Taskmanager.Domain.Task;
import be.ucll.Taskmanager.DTO.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static be.ucll.Taskmanager.Domain.DTOFormatter.DTOToTask;

@Controller
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    SubTaskService subTaskService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/tasks")
    public String getTasks(Model model){
        //Tasks
        List<Task> tasks = new ArrayList<>();
        for (TaskDTO t:taskService.getAll()){
            tasks.add(DTOFormatter.DTOToTask(t));
        }
        Map<Long,List<SubTask>> subtasks = new HashMap<>();
        model.addAttribute("tasks",tasks);
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute @Valid TaskDTO task, BindingResult binding){
        if (binding.hasErrors()){
            return "addTask";
        }
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/new")
    public String goToAddPage(Model model){
        model.addAttribute("task",new TaskDTO());
        return "addTask";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskById(Model model, @PathVariable("id") long id){
        model.addAttribute("tasks", taskService.get(id));
        return "tasks";
    }

    @PostMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String goToEdit(Model model,@PathVariable("id") long id){
        model.addAttribute("task", taskService.get(id));
        return "editTask";
    }

    @PostMapping("/tasks/edit/{id}")
    public String edit(@ModelAttribute("task") @Valid TaskDTO task, BindingResult bindingResult, @PathVariable("id") long id){
        if (bindingResult.hasErrors()){
            return "editTask";
        }
        TaskDTO t  = taskService.get(id);
        task.setSubtasks(t.getSubtasks());
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/sub/create")
    public String goToSubTask(@PathVariable("id") long id,Model model){
        model.addAttribute("task", taskService.get(id));
        model.addAttribute("subtask",new SubTaskDTO());
        return "addSub";
    }

    @PostMapping("/tasks/{id}/sub/create")
    public String addSubTask(@ModelAttribute("subtask") @Valid SubTaskDTO subTask, BindingResult bindingResult, @PathVariable("id") long id, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("task",taskService.get(id));
            return "addsubtask";
        }
        subTask.setTask(DTOFormatter.DTOToTask(taskService.get(id)));
        subTaskService.add(subTask);
        return "redirect:/tasks/";
    }

    @GetMapping("/tasks/{id}/sub/edit/{subtaskid}")
    public String goToEditSub(@PathVariable("subtaskid") long subtaskid,Model model,@PathVariable("id") long id){
        model.addAttribute("subtask",subTaskService.get(subtaskid));
        model.addAttribute("task",taskService.get(id));
        return "editSubTask";
    }

    @PostMapping("/tasks/{id}/sub/edit/{subtaskid}")
    public String editSubTask(@ModelAttribute("subtask") @Valid SubTaskDTO subtask, BindingResult bindingResult ,@PathVariable("subtaskid")long subtaskid, @PathVariable("id") long taskid, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("task",taskService.get(taskid));
            return "editSubtask";
        }
        subtask.setTask(DTOFormatter.DTOToTask(taskService.get(taskid)));
        subtask.setId(subtaskid);
        subTaskService.update(subtask);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/sub/remove/{subtaskid}")
    public String removeSubTask(@PathVariable("subtaskid") long subtaskid){
        subTaskService.delete(subtaskid);
        return "redirect:/tasks";
    }
}
