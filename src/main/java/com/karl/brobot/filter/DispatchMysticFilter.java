package com.karl.brobot.filter;

import com.karl.brobot.core.cmd.Command;
import com.karl.brobot.core.cmd.CommandException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * 调度过滤器
 *
 * @author karl
 * @version 2019-04-24
 */
@Slf4j
public class DispatchMysticFilter extends BaseMysticFilter {
    @Getter
    private List<Command> commands;

    public DispatchMysticFilter(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    protected void doFilterInternal(Context context, WebDriver webDriver) throws FilterException {
        for (Command cmd : getCommands()) {
            final String name = cmd.name();
            final String desc = cmd.desc();
            context.addLink(new FilterLink().setName(name).setDesc(desc));
            try {
                log.debug("执行[{}]:[{}]-->{}", cmd.id(), cmd.name(), cmd.action());
                cmd.execute(webDriver);
            } catch (CommandException e) {
                throw new FilterException(e);
            }
        }
    }

    @Override
    protected void afterFilter(Context context, WebDriver webDriver) {
        StringBuffer buffer = new StringBuffer();
        //[ip:port]
        buffer.append("代理：[").append(context.getIp().getHost()).append(":").append(context.getIp().getPort()).append("]:").append(context.getId());
        buffer.append("，总耗时：");
        buffer.append(Duration.between(Instant.now(), Instant.ofEpochMilli(context.getStartTime())));
        buffer.append("s.");
        log.info(buffer.toString());
    }
}
