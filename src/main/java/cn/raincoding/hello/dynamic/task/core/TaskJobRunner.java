package cn.raincoding.hello.dynamic.task.core;

import cn.raincoding.hello.dynamic.task.common.enums.TaskJobStatus;
import cn.raincoding.hello.dynamic.task.entity.TaskJob;
import cn.raincoding.hello.dynamic.task.service.TaskJobService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zengqm01
 * @date 2022/3/3 08:53
 */
@Log4j2
@AllArgsConstructor
@Component
public class TaskJobRunner implements ApplicationRunner {

    private final TaskJobService taskJobService;

    private final CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<TaskJob> taskJobs = taskJobService.lambdaQuery()
                .eq(TaskJob::getJobStatus, TaskJobStatus.NORMAL.ordinal())
                .list();
        if (!CollectionUtils.isEmpty(taskJobs)) {
            for (TaskJob taskJob : taskJobs) {
                SchedulingTask task = new SchedulingTask(taskJob.getBeanName(), taskJob.getMethodName(), taskJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, taskJob.getCronExpression());
            }

            log.info("定时任务加载完毕...");
        }
    }
}
