package cn.raincoding.hello.dynamic.task.service;

import cn.raincoding.hello.dynamic.task.entity.TaskJob;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zengqm01
 * @date 2022/3/2 10:10
 */
public interface TaskJobService extends IService<TaskJob> {

    void addTaskJob(TaskJob taskJob);

    void updateTaskJob(TaskJob taskJob);

    void removeTaskJob(Long jobId);

    void updateTaskJobStatus(Long jobId, Integer jobStatus);

}
