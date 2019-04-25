package com.karl.brobot.filter;

import com.karl.brobot.ip.ProxyIpManager;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;

/**
 * 最后执行过滤器，关闭浏览器
 *
 * @author karl
 * @version 2019-04-24
 */
@Slf4j
public class EndMysticFilter implements MysticFilter {
    @Setter
    private ApplicationContext applicationContext;
    @Setter
    private ProxyIpManager proxyIpManager;

    @Override
    public void doFilter(Context context, WebDriver webDriver, FilterChain filterChain) throws FilterException {
        try {
            filterChain.doFiler(context, webDriver);
        } catch (Throwable e) {
            log.warn("执行异常(可忽略)，将关闭浏览器，错误信息: [{}]", e.getMessage());
        } finally {
            webDriver.quit();
            //如果步数超过3，再次使用该ip
            if (proxyIpManager != null && context.getStep() >= 3) {
                log.info("重用IP [{}:{}]", context.getIp().getHost(), context.getIp().getPort());
                proxyIpManager.put(context.getIp());
            }
            //发布完成调度事件
            if (applicationContext != null) {
                applicationContext.publishEvent(new FinishDispatchEvent(context, this));
            }
        }
    }
}
