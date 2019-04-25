package com.karl.brobot.ip;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 实现队列进行管理ip，允许超过进行放入
 *
 * @author karl
 * @version 2019-04-23
 */
@Slf4j
public class DefaultProxyIpManager implements ProxyIpManager {
    /**
     * 最大容量
     */
    public static final int MAX_SIZE = 350;
    /**
     * 最大值
     */
    @Getter
    @Setter
    private int maxSize = MAX_SIZE;

    /**
     * ip检查器
     */
    private IpChecker ipChecker;

    public DefaultProxyIpManager(IpChecker ipChecker) {
        this.ipChecker = ipChecker;
    }

    private BlockingQueue<IpInfo> queue = new LinkedBlockingQueue<>();

    @Override
    public boolean enough() {
        return queue.size() >= getMaxSize();
    }

    @Override
    public IpInfo get() {
        try {
            IpInfo ip = queue.poll(2, TimeUnit.MINUTES);
            if (ip == null) {
                Thread.sleep(200);
                return get();
            }
            if (ipChecker.check(ip)) {
                return ip;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void free(IpInfo ip) {
        //不进行处理
    }

    @Override
    public boolean put(IpInfo ip) {
        queue.add(ip);
        log.debug("放入IP：[{}:{}]", ip.getHost(), ip.getPort());
        return enough();
    }
}
