package com.karl.brobot.core;

import com.karl.brobot.core.project.ProjectStarter;
import com.karl.brobot.filter.FinishDispatchEvent;
import com.karl.brobot.ip.IpInfo;
import com.karl.brobot.ip.ProxyIpManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自动调度开始
 *
 * @author karl
 * @version 2019-04-24
 */
@Component
@Slf4j
public class AutoDispatch {
    /**
     * 机器人最大数
     */
    @Value("${robot.max_count:5}")
    private int maxMachine;
    @Autowired
    private ProxyIpManager proxyIpManager;
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private ProjectStarter projectStarter;
    /**
     * 启动机器人
     */
    @Value("${robot.enable:true}")
    private boolean robotEnable = true;
    /**
     * 机器人名称
     */
    private Queue<String> machineQueue = new ConcurrentLinkedQueue();
    /**
     * 计算器
     */
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 启动完成，启动任务
     *
     * @param event
     */
    @EventListener
    public void start(ApplicationStartedEvent event) {
        if(!robotEnable) {
            return;
        }
        log.info("启动完成，机器人已经开始出发。");
        while (!isMax()) {
            taskScheduler.execute(this::run);
        }
    }

    /**
     * 收到有进程结束，再次进行启动
     *
     * @param event
     */
    @EventListener
    @Async(value = "taskScheduler")
    public void start(FinishDispatchEvent event) {
        count.decrementAndGet();
        machineQueue.add(event.getContext().getName());
        run();
    }

    /**
     * 开始启动任务
     */
    private void run() {
        try {
            if (isMax()) {
                return;
            }
            //当前机器人数
            count.incrementAndGet();
            String machineNo = machineQueue.poll();
            log.info("机器人{}号出发。", machineNo);
            final IpInfo ipInfo = proxyIpManager.get();

            if (ipInfo == null) {
                log.warn("找到ip为空，继续执行");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                count.decrementAndGet();
                machineQueue.add(machineNo);
                run();
                return;
            }
            projectStarter.start(ipInfo, machineNo);
        } catch (Exception e) {
            log.debug("", e);
        }
    }

    /**
     * 是否最大值
     *
     * @return
     */
    private boolean isMax() {
        return count.get() >= maxMachine;
    }

    /**
     * 机器人名称
     */
    @PostConstruct
    private void afterInit() {
        for (int i = 0; i < maxMachine; i++) {
            machineQueue.add(Integer.toString(i + 1));
        }
    }
}
