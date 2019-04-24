package com.karl.brobot.filter;

import org.openqa.selenium.WebDriver;

/**
 * 基础过滤链路
 *
 * @author karl
 * @version 2019-04-24
 */
public abstract class BaseMysticFilter implements MysticFilter {
    @Override
    public void doFilter(Context context, WebDriver webDriver, FilterChain filterChain) throws FilterException {
        doFilterInternal(context, webDriver);
        filterChain.doFiler(context, webDriver);
        afterFilter(context, webDriver);
    }

    /**
     * 内部过滤前置
     *
     * @param context
     * @param webDriver
     * @throws FilterException
     */
    protected abstract void doFilterInternal(Context context, WebDriver webDriver) throws FilterException;

    /**
     * 后置处理
     *
     * @param webDriver
     */
    protected void afterFilter(Context context, WebDriver webDriver) {

    }
}
