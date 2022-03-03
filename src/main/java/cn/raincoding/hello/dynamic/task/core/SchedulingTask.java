package cn.raincoding.hello.dynamic.task.core;

import cn.raincoding.hello.dynamic.task.common.util.SpringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author zengqm01
 * @date 2022/3/1 16:16
 */
@Log4j2
public class SchedulingTask implements Runnable {

    private final String beanName;

    private final String methodName;

    private final String params;

    public SchedulingTask(String beanName, String methodName) {
        this(beanName, methodName, null);
    }

    public SchedulingTask(String beanName, String methodName, String params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
    }

    @Override
    public void run() {
        log.info("定时任务开始执行 - bean: {}, 方法: {}, 参数: {}", beanName, methodName, params);
        long startTime = System.currentTimeMillis();

        try {
            Object bean = SpringUtils.getBean(beanName);

            Method method;
            if (StringUtils.isNotEmpty(params)) {
                method = bean.getClass().getDeclaredMethod(methodName, String.class);
            } else {
                method = bean.getClass().getDeclaredMethod(methodName);
            }

            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotEmpty(params)) {
                method.invoke(bean, params);
            } else {
                method.invoke(bean);
            }
        } catch (Exception e) {
            log.error("定时任务执行异常 - bean: {}, 方法: {}, 参数: {}", beanName, methodName, params, e);
        }

        long costTime = System.currentTimeMillis() - startTime;
        log.info("定时任务执行结束 - bean: {}, 方法: {}, 参数: {}, 耗时: {} 毫秒", beanName, methodName, params, costTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SchedulingTask that = (SchedulingTask) o;

        return new EqualsBuilder()
                .append(beanName, that.beanName)
                .append(methodName, that.methodName)
                .append(params, that.params)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(beanName)
                .append(methodName)
                .append(params)
                .toHashCode();
    }

}
