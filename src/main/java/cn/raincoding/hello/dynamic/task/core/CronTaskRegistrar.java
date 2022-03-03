package cn.raincoding.hello.dynamic.task.core;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengqm01
 * @date 2022/3/2 09:36
 */
@AllArgsConstructor
@Component
public class CronTaskRegistrar implements DisposableBean {

    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    private final TaskScheduler taskScheduler;

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public void addCronTask(Runnable task, String cronExpression) {
        addCronTask(new CronTask(task, cronExpression));
    }

    public void addCronTask(CronTask cronTask) {
        if (ObjectUtils.isNotEmpty(cronTask)) {
            Runnable task = cronTask.getRunnable();
            if (scheduledTasks.containsKey(task)) {
                removeCronTask(task);
            }

            scheduledTasks.put(task, scheduledCronTask(cronTask));
        }
    }

    public void removeCronTask(Runnable task) {
        ScheduledTask scheduledTask = scheduledTasks.remove(task);
        if (ObjectUtils.isNotEmpty(scheduledTask)) {
            scheduledTask.cancel();
        }
    }

    public ScheduledTask scheduledCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());

        return scheduledTask;
    }

    @Override
    public void destroy() throws Exception {
        for (ScheduledTask scheduledTask : scheduledTasks.values()) {
            scheduledTask.cancel();
        }

        scheduledTasks.clear();
    }
}
