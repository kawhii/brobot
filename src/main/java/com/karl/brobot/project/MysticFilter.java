package com.karl.brobot.project;

import org.openqa.selenium.WebDriver;

/**
 * 神秘人filter
 *
 * @author karl
 * @version 2019-04-24
 */
public interface MysticFilter {
    void doFilter(Context context, WebDriver webDriver, FilterChain filterChain) throws FilterException;
}
