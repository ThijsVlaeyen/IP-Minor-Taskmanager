package be.ucll.Taskmanager.controller;

import be.ucll.Taskmanager.DB.SubTaskService;
import be.ucll.Taskmanager.DB.TaskService;
import be.ucll.Taskmanager.DTO.SubTaskDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    SubTaskService subTaskService;

    @GetMapping("/db")
    public String dbtest() {
        // create default tasks
        Task ipminor = new Task("finish ip minor", "complete crud operation", LocalDateTime.of(2020, 3, 17, 14, 30));
        SubTask changeGet = new SubTask("get to post", "change all faulty get to post request");
        SubTask showSub = new SubTask("show SubTask", "make a page to show subtasks, if you see this it means you can mark this task done");
        SubTask validation = new SubTask("validation", "add validation to each form");
        taskService.add(createDTOfromTask(ipminor));
        validation.setTaskid(1);
        changeGet.setTaskid(1);
        showSub.setTaskid(1);
        subTaskService.add(createDTOfromSubtask(changeGet));
        subTaskService.add(createDTOfromSubtask(showSub));
        subTaskService.add(createDTOfromSubtask(validation));
        return "db";
    }

    @GetMapping("/tasks")
    public String getTasks(Model model){
        List<Task> tasks = taskService.getAll();
        Map<Long,List<SubTask>> subtasks = new HashMap<>();
        model.addAttribute("tasks",tasks);
        for (Task t : tasks){
            List<SubTask> subTaskList = subTaskService.getAll(t.getId());
            if(subTaskList.size()>0)
            System.out.println(subTaskList.get(0).isCompleted());

            subtasks.put(t.getId(),subTaskList);
        }
        model.addAttribute("subtasks",subtasks);
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute @Valid Task task, BindingResult binding){
        if (binding.hasErrors()){
            return "addTask";
        }
        taskService.add(createDTOfromTask(task));
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskById(Model model, @PathVariable("id") long id){
        model.addAttribute("task", taskService.get(id));
        model.addAttribute("subtasks", subTaskService.getAll(id));
        return "tasks";
    }

    @GetMapping("/tasks/new")
    public String goToAddPage(Model model){
        System.out.println("test");
        model.addAttribute("task",new Task());
        return "addTask";
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
    public String edit(@ModelAttribute Task task, @PathVariable("id") long id){
        Task t  = taskService.get(id);
        task.setSubtasks(t.getSubtasks());
        taskService.update(createDTOfromTask(task));
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/sub/create")
    public String goToSubTask(@PathVariable("id") long id,Model model){
        model.addAttribute("task", taskService.get(id));
        return "addSub";
    }

    @PostMapping("/tasks/{id}/sub/create")
    public String addSubTask(@ModelAttribute SubTask subTask, @PathVariable("id") long id){
        subTask.setTaskid(id);
        subTaskService.add(createDTOfromSubtask(subTask));
        System.out.println("subtask taskid:" + subTaskService.getAll().get(0).getTaskid());
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/sub/remove/{subtaskid}")
    public String removeSubTask(@PathVariable("subtaskid") long subtaskid){
        subTaskService.delete(subtaskid);
        return "redirect:/tasks";
    }

//    @GetMapping("/tasks/{id}/sub/edit/{subtaskid}")
//    public String goToEdit(@PathVariable("subtaskid") long subtaskid,Model model,@PathVariable("id") long id){
//        model.addAttribute("subtask",subTaskService.get(subtaskid));
//        model.addAttribute("task",taskService.get(id));
//        return "editSubTask";
//    }

//    @PostMapping("/tasks/{id}/sub/edit/{subtaskid}")
//    public String editSubTask(@ModelAttribute SubTask subtask,@PathVariable("subtaskid")long subtaskid, @PathVariable("id") long taskid){
//        subtask.setTaskid(taskid);
//        subtask.setId(subtaskid);
//        subTaskService.update(createDTOfromSubtask(subtask));
//        return "redirect:/tasks";
//    }

    private SubTaskDTO createDTOfromSubtask(SubTask subTask) {
        SubTaskDTO dto = new SubTaskDTO();
        dto.setId(subTask.getId());
        dto.setDescription(subTask.getDescription());
        dto.setName(subTask.getName());
        dto.setTaskid(subTask.getTaskid());
        dto.setCompleted(subTask.isCompleted());
        return dto;
    }

    private TaskDTO createDTOfromTask(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setName(task.getName());
        dto.setDeadline(task.getDeadline());
        dto.setCompleted(task.isCompleted());
        dto.setSubtasks(task.getSubtasks());
        return dto;
    }
}
