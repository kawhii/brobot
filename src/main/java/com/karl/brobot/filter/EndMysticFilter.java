package com.karl.brobot.filter;

import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;

/**
 * 最后执行过滤器，关闭浏览器
 *
 * @author karl
 * @version 2019-04-24
 */
public class EndMysticFilter implements MysticFilter {
    @Setter
    private ApplicationContext applicationContext;

    @Override
    public void doFilter(Context context, WebDriver webDriver, FilterChain filterChain) throws FilterException {
        try {
            filterChain.doFiler(context, webDriver);
        } finally {
            webDriver.close();
            //发布完成调度事件
            if(applicationContext != null) {
                applicationContext.publishEvent(new FinishDispatchEvent(context, this));
            }
        }
    }
}
