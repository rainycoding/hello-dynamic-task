package cn.raincoding.hello.dynamic.task.service.impl;

import cn.raincoding.hello.dynamic.task.common.enums.TaskJobStatus;
import cn.raincoding.hello.dynamic.task.core.CronTaskRegistrar;
import cn.raincoding.hello.dynamic.task.core.SchedulingTask;
import cn.raincoding.hello.dynamic.task.entity.TaskJob;
import cn.raincoding.hello.dynamic.task.mapper.TaskJobMapper;
import cn.raincoding.hello.dynamic.task.service.TaskJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zengqm01
 * @date 2022/3/2 10:17
 */
@AllArgsConstructor
@Service
public class TaskJobServiceImpl extends ServiceImpl<TaskJobMapper, TaskJob> implements TaskJobService {

    private final CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void addTaskJob(TaskJob taskJob) {
        int row = baseMapper.insert(taskJob);
        if (row == 1) {
            if (taskJob.getJobStatus().equals(TaskJobStatus.NORMAL.ordinal())) {
                SchedulingTask task = new SchedulingTask(taskJob.getBeanName(), taskJob.getMethodName(), taskJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, taskJob.getCronExpression());
            }
        }
    }

    @Override
    public void updateTaskJob(TaskJob taskJob) {
        TaskJob existedTaskJob = baseMapper.selectById(taskJob.getJobId());
        int row = baseMapper.updateById(taskJob);
        if (row == 1) {
            if (existedTaskJob.getJobStatus().equals(TaskJobStatus.NORMAL.ordinal())) {
                SchedulingTask task = new SchedulingTask(existedTaskJob.getBeanName(), existedTaskJob.getMethodName(), existedTaskJob.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }
            if (taskJob.getJobStatus().equals(TaskJobStatus.NORMAL.ordinal())) {
                SchedulingTask task = new SchedulingTask(taskJob.getBeanName(), taskJob.getMethodName(), taskJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, taskJob.getCronExpression());
            }
        }
    }

    @Override
    public void removeTaskJob(Long jobId) {
        TaskJob taskJob = baseMapper.selectById(jobId);
        int row = baseMapper.deleteById(jobId);
        if (row == 1) {
            if (taskJob.getJobStatus().equals(TaskJobStatus.NORMAL.ordinal())) {
                SchedulingTask task = new SchedulingTask(taskJob.getBeanName(), taskJob.getMethodName(), taskJob.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }
        }
    }

    @Override
    public void updateTaskJobStatus(Long jobId, Integer jobStatus) {
        TaskJob taskJob = baseMapper.selectById(jobId);
        boolean flag = baseMapper.updateJobStatus(jobId, jobStatus);
        if (flag) {
            SchedulingTask task = new SchedulingTask(taskJob.getBeanName(), taskJob.getMethodName(), taskJob.getMethodParams());
            if (jobStatus.equals(TaskJobStatus.NORMAL.ordinal())) {
                cronTaskRegistrar.addCronTask(task, taskJob.getCronExpression());
            } else {
                cronTaskRegistrar.removeCronTask(task);
            }
        }
    }
}
