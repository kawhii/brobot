package com.karl.brobot.core.cmd;

import com.karl.brobot.filter.Context;
import org.openqa.selenium.WebDriver;

/**
 * 指令寻找器
 *
 * @author karl
 * @version 2019-04-24
 */
public interface CommandFinder {
    /**
     * 查找指令
     *
     * @param context 当前上下文
     * @param webDriver 浏览器驱动
     * @param lastCmd 上一个指令
     * @return 最新指令
     */
    Command find(Context context, WebDriver webDriver, Command lastCmd);
}
