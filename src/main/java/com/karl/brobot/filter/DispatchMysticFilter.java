package com.karl.brobot.filter;

import com.karl.brobot.core.cmd.Command;
import com.karl.brobot.core.cmd.CommandException;
import com.karl.brobot.core.cmd.CommandFinder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.Instant;

/**
 * 调度过滤器
 *
 * @author karl
 * @version 2019-04-24
 */
@Slf4j
public class DispatchMysticFilter extends BaseMysticFilter {
    @Getter
    private Command startCmd;
    @Getter
    @Setter
    private CommandFinder commandFinder;

    public DispatchMysticFilter(Command startCmd) {
        this.startCmd = startCmd;
    }

    @Override
    protected void doFilterInternal(Context context, WebDriver webDriver) throws FilterException {
        Command cmd = null;
        //如果还没溢出的情况下，继续寻找
        while (!context.isFull()) {
            if (context.getStep() == 0) {
                cmd = getStartCmd();
            } else {
                if (commandFinder == null) {
                    return;
                }
                //寻找下一个
                cmd = commandFinder.find(context, webDriver, cmd);
                if (cmd == null) {
                    return;
                }
            }
            final String name = cmd.name();
            final String desc = cmd.desc();
            context.addLink(new FilterLink().setName(name).setDesc(desc));
            context.next();
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
        buffer.append("代理：[").append(context.getIp().getHost()).append(":").append(context.getIp().getPort()).append(
                "]:").append(context.getId());
        buffer.append("，总耗时：");
        buffer.append(Duration.between(Instant.now(), Instant.ofEpochMilli(context.getStartTime())));
        buffer.append("s.");
        log.info(buffer.toString());
    }
}
