package com.karl.brobot.core.cmd;

/**
 * 命令匹配
 *
 * @author karl
 * @version 2019-04-24
 */
public interface CommandMatcher {
    /**
     * 命令匹配
     *
     * @param webInfo      当前web情况
     * @param platformCode 平台编码
     * @param last         最后一个指令
     * @return
     * @author huangwenbin
     */
    boolean matcher(WebInfo webInfo, String platformCode, Command last);
}
