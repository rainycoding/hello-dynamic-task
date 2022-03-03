package cn.raincoding.hello.dynamic.task.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zengqm01
 * @date 2022/3/2 10:06
 */
@Setter
@Getter
public class TaskJob {

    /**
     * 任务 ID
     */
    @TableId
    private Long jobId;

    /**
     * 任务 bean 名称
     */
    private String beanName;

    /**
     * 任务方法名称
     */
    private String methodName;

    /**
     * 任务方法参数
     */
    private String methodParams;

    /**
     * 任务 Cron 表达式
     */
    private String cronExpression;

    /**
     * 备注
     */
    private String remark;

    /**
     * 任务状态（1：正常    0：暂停）
     */
    private Integer jobStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
