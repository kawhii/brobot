package com.karl.brobot.core;

import com.karl.brobot.ip.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    @Value("${ip.search.enable:true}")
    private boolean searchIp;
    /**
     * 当前需要处理的ip数量
     */
    private AtomicInteger ipCheckCount = new AtomicInteger(0);
    /**
     * 正在处理ip量最多两百
     */
    public static final int IP_CHECKING_MAX = 100;

    /**
     * 40秒查询一次ip
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void searchIp() {
        if (!searchIp) {
            return;
        }
        if (proxyIpManager.enough()) {
            log.info("代理IP量已足够，本次暂停搜寻");
            return;
        }
        log.debug("正在检查ip数：{}", ipCheckCount.get());
        //发送监听，为了异步执行
        ipProviderList.forEach(provider -> applicationContext.publishEvent(new IpSearchStartListener(this, provider)));
    }

    /**
     * 异步查询ip
     *
     * @param listener
     */
    @Async(value = "taskScheduler")
    @EventListener
    public void search(IpSearchStartListener listener) {
        if (ipCheckCount.get() >= IP_CHECKING_MAX) {
            return;
        }

        IpProvider provider = listener.getProvider();
        log.info("[{} ---> {}]开始寻找代理ip", provider.name(), provider.url());
        try {
            final List<IpInfo> list;
            try {
                list = provider.provide();
            } catch (Exception e) {
                log.error("[{}:{}]获取代理ip异常", provider.name(), provider.id());
                return;
            }
            if (list == null) {
                return;
            }
            log.info("[{}]获取代理ip总数:{}", provider.name(), list.size());
            int current = ipCheckCount.get();
            //最新处理数量
            ipCheckCount.set(current + list.size());
            //检测ip健康则放入队列
            list.forEach(ipInfo -> {
                //新增一个ip count
                if (!proxyIpManager.enough() && ipChecker.check(ipInfo)) {
                    proxyIpManager.put(ipInfo);
                }
                //减掉ip处理数量
                ipCheckCount.decrementAndGet();
            });
        } catch (Exception e) {
            log.warn("[{}寻找发生错误]", provider.name(), e);
        }
    }
}
