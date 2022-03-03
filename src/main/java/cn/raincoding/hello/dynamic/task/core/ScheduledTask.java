package cn.raincoding.hello.dynamic.task.core;

import org.apache.commons.lang3.ObjectUtils;

import java.util.concurrent.ScheduledFuture;

/**
 * @author zengqm01
 * @date 2022/3/1 16:11
 */
public final class ScheduledTask {

    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (ObjectUtils.isNotEmpty(future)) {
            future.cancel(true);
        }
    }

}
