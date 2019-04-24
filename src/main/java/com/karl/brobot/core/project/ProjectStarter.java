package com.karl.brobot.core.project;

import com.karl.brobot.ip.IpInfo;

/**
 * 项目启动
 *
 * @author karl
 * @version 2019-04-24
 */
public interface ProjectStarter {
    /**
     * 项目启动信息
     *
     * @param ipInfo
     */
    void start(IpInfo ipInfo, String name);
}
