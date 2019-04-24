package com.karl.brobot.filter;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 完成时进行调度
 *
 * @author karl
 * @version 2019-04-24
 */
public class FinishDispatchEvent extends ApplicationEvent {
    @Getter
    private Context context;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public FinishDispatchEvent(Context context, Object source) {
        super(source);
        this.context = context;
    }
}
