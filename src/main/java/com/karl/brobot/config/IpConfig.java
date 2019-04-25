package com.karl.brobot.config;

import com.karl.brobot.ip.DefaultIpChecker;
import com.karl.brobot.ip.DefaultProxyIpManager;
import com.karl.brobot.ip.IpChecker;
import com.karl.brobot.ip.ProxyIpManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ip管理中心
 *
 * @author karl
 * @version 2019-04-23
 */
@Configuration
public class IpConfig {
    /**
     * 代理ip管理
     *
     * @return
     */
    @Bean
    protected ProxyIpManager proxyIpManager(IpChecker ipChecker) {
        return new DefaultProxyIpManager(ipChecker);
    }

    /**
     * ip检测器
     *
     * @return
     */
    @Bean
    protected IpChecker ipChecker() {
        return new DefaultIpChecker();
    }
}
