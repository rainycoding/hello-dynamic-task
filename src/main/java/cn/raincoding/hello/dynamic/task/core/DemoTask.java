package cn.raincoding.hello.dynamic.task.core;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @author zengqm01
 * @date 2022/3/2 09:56
 */
@Log4j2
@Component
public class DemoTask {

    public void taskWithParams(String params) {
        log.info("执行有参示例任务: {}", params);
    }

    public void taskNoParams() {
        log.info("执行无参示例任务");
    }

}
