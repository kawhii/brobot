package com.karl.brobot.filter;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 过滤链路信息
 *
 * @author karl
 * @version 2019-04-24
 */
@Data
@Accessors(chain = true)
public class FilterLink {
    /**
     * 处理名称
     */
    private String name;
    /**
     * 处理描述
     */
    private String desc;
}
