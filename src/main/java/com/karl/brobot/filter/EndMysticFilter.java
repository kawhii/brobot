package com.karl.brobot.filter;

import org.openqa.selenium.WebDriver;

/**
 * 最后执行过滤器，关闭浏览器
 *
 * @author karl
 * @version 2019-04-24
 */
public class EndMysticFilter implements MysticFilter {

    @Override
    public void doFilter(Context context, WebDriver webDriver, FilterChain filterChain) throws FilterException {
        try {
            filterChain.doFiler(context, webDriver);
        } finally {
            webDriver.close();
        }
    }
}
