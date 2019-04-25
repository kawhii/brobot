package com.karl.brobot.core.cmd;

import lombok.Getter;

/**
 * 处理动作
 *
 * @author karl
 * @version 2019-04-24
 */
public enum Action {
    /**
     * 点击
     */
    CLICK("点击"),
    /**
     * 提交
     */
    SUBMIT("提交"),
    /**
     * 输入
     */
    INPUT("输入"),
    /**
     * 打开
     */
    OPEN("打开"),
    /**
     * 打开标签
     */
    NEW_TAB("打开标签"),
    /**
     * 休眠
     */
    SLEEP("休眠"),
    /**
     * 滑动
     */
    SCROLL("滑动"),
    /**
     * 后退
     */
    BACK("后退"),
    /**
     * 前进
     */
    FORWARD("前进"),
    /**
     * 刷新
     */
    REFRESH("刷新"),
    ;
    @Getter
    String name;

    Action(String name) {
        this.name = name;
    }
}
