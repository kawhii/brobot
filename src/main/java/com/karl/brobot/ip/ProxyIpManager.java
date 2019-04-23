package com.karl.brobot.ip;

/**
 * 代理ip管理者
 *
 * @author karl
 * @version 2019-04-23
 */
public interface ProxyIpManager {
    /**
     * 是否已足够不允许放入
     *
     * @return
     */
    boolean enough();

    /**
     * 获取一个ip
     *
     * @return
     */
    IpInfo get();

    /**
     * 释放ip
     *
     * @param ip
     */
    void free(IpInfo ip);

    /**
     * 放入ip
     *
     * @param ip
     * @return
     */
    boolean put(IpInfo ip);
}
