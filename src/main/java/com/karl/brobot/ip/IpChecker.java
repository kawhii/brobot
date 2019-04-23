package com.karl.brobot.ip;

/**
 * 检查ip端口是否可用
 *
 * @author karl
 * @version 2019-04-23
 */
public interface IpChecker {
    /**
     * 连接超时5秒
     */
    int CONNECT_TIMEOUT_SECONDS = 5;

    /**
     * 检查是否可用
     *
     * @param ip ip信息
     * @return
     * @author huangwenbin
     */
    boolean check(IpInfo ip);
}
