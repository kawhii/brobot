package com.karl.brobot.filter;

import org.openqa.selenium.WebDriver;

/**
 * 神秘人filter
 *
 * @author karl
 * @version 2019-04-24
 */
public interface MysticFilter {
    /**
     * 过滤
     *
     * @param context 过滤上下文
     * @param webDriver 浏览器驱动
     * @param filterChain 过滤链
     * @throws FilterException
     */
    void doFilter(Context context, WebDriver webDriver, FilterChain filterChain) throws FilterException;
}
