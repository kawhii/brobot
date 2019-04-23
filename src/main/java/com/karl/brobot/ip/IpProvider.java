package com.karl.brobot.ip;

import java.util.List;

/**
 * ip提供信息
 *
 * @author karl
 * @version 2019-04-23
 */
public interface IpProvider {
    /**
     * ip提供者信息
     *
     * @return
     */
    String name();

    /**
     * 获取ip信息
     *
     * @return
     */
    List<IpInfo> provide();
}
