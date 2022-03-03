package cn.raincoding.hello.dynamic.task.controller;

import cn.raincoding.hello.dynamic.task.entity.TaskJob;
import cn.raincoding.hello.dynamic.task.service.TaskJobService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengqm01
 * @date 2022/3/2 18:29
 */
@AllArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskJobController {

    private final TaskJobService taskJobService;

    @PostMapping
    public String addTaskJob(@RequestBody TaskJob taskJob) {
        taskJobService.addTaskJob(taskJob);
        return "success";
    }

    @PutMapping
    public String updateTaskJob(@RequestBody TaskJob taskJob) {
        taskJobService.updateTaskJob(taskJob);
        return "success";
    }

    @PutMapping("/{jobId}/{jobStatus}")
    public String updateTaskJobStatus(@PathVariable("jobId") Long jobId, @PathVariable("jobStatus") Integer jobStatus) {
        taskJobService.updateTaskJobStatus(jobId, jobStatus);
        return "success";
    }

    @DeleteMapping("/jobId")
    public String removeTaskJob(@PathVariable("jobId") Long jobId) {
        taskJobService.removeTaskJob(jobId);
        return "success";
    }

}
