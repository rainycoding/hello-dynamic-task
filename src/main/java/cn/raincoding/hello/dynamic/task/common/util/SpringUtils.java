package cn.raincoding.hello.dynamic.task.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zengqm01
 * @date 2021/9/17 9:39
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.ctx = applicationContext;
    }

    /**
     * 获取指定名称的 Bean
     *
     * @param beanName Bean 名称
     * @return Bean
     */
    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    /**
     * 获取指定类型的 Bean
     *
     * @param clazz Bean Class 对象
     * @param <T> Bean 类型
     * @return Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    /**
     * 获取指定名称和类型的 Bean
     *
     * @param beanName Bean 名称
     * @param clazz Bean Class 对象
     * @param <T> Bean 类型
     * @return Bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return ctx.getBean(beanName, clazz);
    }

    /**
     * 容器中是否包含指定名称的 Bean
     *
     * @param beanName Bean 名称
     * @return 包含返回 true
     */
    public static boolean containsBean(String beanName) {
        return ctx.containsBean(beanName);
    }

    /**
     * 获取当前的环境配置
     *
     * @return 环境配置数组
     */
    public static String[] getActiveProfiles() {
        return ctx.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前环境配置，多个返回第一个
     *
     * @return 当前环境配置
     */
    public static String getActiveProfile() {
        String[] activeProfiles = getActiveProfiles();
        return activeProfiles.length != 0 ? activeProfiles[0] : null;
    }
}
