package com.karl.brobot.core.cmd;

import org.openqa.selenium.WebDriver;

/**
 * 指令动作
 *
 * @author karl
 * @version 2019-04-24
 */
public interface Command {
    /**
     * 当前指令处理动作
     *
     * @return
     */
    Action[] action();

    /**
     * 指令执行
     *
     * @param webDriver
     * @throws CommandException
     */
    void execute(WebDriver webDriver) throws CommandException;

    /**
     * 指令编号
     *
     * @return
     */
    String id();

    /**
     * 匹配指令
     *
     * @return
     */
    CommandMatcher getMatcher();

    /**
     * 指令名称
     *
     * @return
     */
    String name();

    /**
     * 指令描述
     *
     * @return
     */
    default String desc() {
        return "";
    }
}
