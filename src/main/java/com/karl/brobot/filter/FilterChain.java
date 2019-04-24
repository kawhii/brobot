package com.karl.brobot.filter;

import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.util.Iterator;
import java.util.List;

/**
 * 过滤链路
 *
 * @author karl
 * @version 2019-04-24
 */
public class FilterChain {
    private Iterator<MysticFilter> iterator;
    @Getter
    public List<MysticFilter> filters;

    public FilterChain(List<MysticFilter> filters) {
        this.filters = filters;
    }

    private Context context;
    private WebDriver webDriver;

    public void doFiler(Context context, WebDriver webDriver) throws FilterException {
        if (filters == null) {
            return;
        }
        if (this.iterator == null) {
            this.iterator = this.filters.iterator();
        }

        if (this.iterator.hasNext()) {
            MysticFilter nextFilter = this.iterator.next();
            nextFilter.doFilter(context, webDriver, this);
        }
        this.context = context;
        this.webDriver = webDriver;
    }

    public void reset() {
        this.context = null;
        this.webDriver = null;
        this.iterator = null;
    }
}
