package com.karl.brobot.filter;

import com.karl.brobot.ip.IpInfo;
import lombok.Getter;
import lombok.Setter;

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
    private String id;
    @Getter
    private IpInfo ip;
    @Getter
    private long startTime;
    /**
     * 当前步数
     */
    @Getter
    private int step = 0;
    /**
     * 机器人编号
     */
    @Setter
    @Getter
    private String name;
    /**
     * 最大步数
     */
    @Getter
    @Setter
    private int max = 10;

    public Context(String id, IpInfo ip, long startTime) {
        this.id = id;
        this.ip = ip;
        this.startTime = startTime;
    }

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

    /**
     * 下一步
     */
    public void next() {
        step++;
    }

    /**
     * 是否已满
     * @return
     */
    public boolean isFull() {
        return getStep() >= getMax();
    }
}
