package com.karl.brobot.core.cmd;

import com.karl.brobot.filter.Context;
import org.openqa.selenium.WebDriver;

/**
 * 默认指令寻找器
 *
 * @author karl
 * @version 2019-04-24
 */
public class DefaultCommandFinder implements CommandFinder {
    @Override
    public Command find(Context context, WebDriver webDriver, Command lastCmd) {
        return null;
    }
}