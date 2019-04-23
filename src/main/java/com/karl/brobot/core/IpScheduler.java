package com.karl.brobot.core;

import com.karl.brobot.ip.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ip获取定时任务
 *
 * @author karl
 * @version 2019-04-23
 */
@Component
@Slf4j
public class IpScheduler {
    /**
     * ip寻找列表
     */
    @Autowired
    private List<IpProvider> ipProviderList;
    @Autowired
    private IpChecker ipChecker;
    @Autowired
    private ProxyIpManager proxyIpManager;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 一分钟查询一次ip
     */
    @Scheduled(cron = "0 */1 * * * ?")
    @Async
    public void searchIp() {
        if(proxyIpManager.enough()) {
            log.info("代理IP量已足够，本次暂停搜寻");
            return;
        }
        //发送监听，为了异步执行
        ipProviderList.forEach(provider -> applicationContext.publishEvent(new IpSearchStartListener(this, provider)));
    }

    /**
     * 异步查询ip
     * @param listener
     */
    @Async
    @EventListener
    public void search(IpSearchStartListener listener) {
        IpProvider provider = listener.getProvider();
        log.info("[{} ---> {}]开始寻找代理ip", provider.name(), provider.url());
        try {
            final List<IpInfo> list = provider.provide();
            if(list == null) {
                return;
            }
            log.info("[{}]获取代理ip总数:{}", provider.name(), list.size());
            //检测ip健康则放入队列
            list.forEach(ipInfo -> {
                if(ipChecker.check(ipInfo)) {
                    proxyIpManager.put(ipInfo);
                }
            });
        } catch (Exception e) {
            log.warn("[{}寻找发生错误]", provider.name(), e);
        }
    }
}
