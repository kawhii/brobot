package com.karl.brobot.core.cmd;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 窗口信息
 *
 * @author karl
 * @version 2019-04-24
 */
@Accessors(chain = true)
@Data
public class WebInfo {
    /**
     * 当前地址
     */
    private String currentUrl;
    /**
     * 当前标题
     */
    private String title;

}
