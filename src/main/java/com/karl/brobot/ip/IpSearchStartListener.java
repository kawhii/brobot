package com.karl.brobot.ip;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author karl
 * @version 2019-04-24
 */
public class IpSearchStartListener extends ApplicationEvent {
    @Getter
    private IpProvider provider;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public IpSearchStartListener(Object source, IpProvider provider) {
        super(source);
        this.provider = provider;
    }
}
