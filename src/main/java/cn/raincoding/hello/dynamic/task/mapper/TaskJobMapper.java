package cn.raincoding.hello.dynamic.task.mapper;

import cn.raincoding.hello.dynamic.task.entity.TaskJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zengqm01
 * @date 2022/3/2 10:18
 */
@Mapper
public interface TaskJobMapper extends BaseMapper<TaskJob> {

    default boolean updateJobStatus(Long jobId, Integer jobStatus) {
        return new LambdaUpdateChainWrapper<>(this)
                .set(TaskJob::getJobStatus, jobStatus)
                .eq(TaskJob::getJobId, jobId)
                .update();
    }

}
