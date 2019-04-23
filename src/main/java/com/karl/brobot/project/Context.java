package com.karl.brobot.project;

import com.karl.brobot.ip.IpInfo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤上下文
 *
 * @author karl
 * @version 2019-04-24
 */
public class Context {
    @Getter
    private IpInfo ip;
    @Getter
    private long startTime;
    /**
     * 处理链路
     */
    @Getter
    private List<FilterLink> links = new ArrayList<>();

    /**
     * 添加处理链路
     *
     * @param link
     */
    public void addLink(FilterLink link) {
        links.add(link);
    }
}
