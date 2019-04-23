package com.karl.brobot.ip;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ip信息
 *
 * @author karl
 * @version 2019-04-23
 */
@Data
@Accessors(chain = true)
public class IpInfo {
    /**
     * host
     */
    private String host;
    /**
     * port
     */
    private Integer port;
    /**
     * 创建时间
     */
    private long createTime = System.currentTimeMillis();

    /**
     * 地址
     */
    private String address;

    /**
     * 运营商
     */
    private String isp;

    /**
     * 提供商
     */
    private String provider;
}
