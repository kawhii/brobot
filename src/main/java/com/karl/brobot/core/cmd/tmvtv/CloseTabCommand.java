package com.karl.brobot.core.cmd.tmvtv;

import com.karl.brobot.core.cmd.Action;
import com.karl.brobot.core.cmd.Command;
import com.karl.brobot.core.cmd.CommandException;
import com.karl.brobot.core.cmd.CommandMatcher;
import org.openqa.selenium.WebDriver;

import java.util.Set;

/**
 * @author karl
 * @version 2019-04-25
 */
public class CloseTabCommand implements Command {
    @Override
    public Action[] action() {
        return new Action[]{Action.BACK};
    }

    @Override
    public void execute(WebDriver webDriver) throws CommandException {
        final Set<String> windowHandles = webDriver.getWindowHandles();
        if(windowHandles != null && windowHandles.size() > 1) {
            //关闭当前窗口
            webDriver.close();
        }
    }

    @Override
    public String id() {
        return "tmvtv";
    }

    @Override
    public CommandMatcher getMatcher() {
        //上一个标签有tab就可以
        return (webInfo, platformCode, last, wd) -> {
            if (last == null) {
                return false;
            }
            for (Action action : last.action()) {
                if (action == Action.NEW_TAB) {
                    return true;
                }
            }
            return false;
        };
    }

    @Override
    public String name() {
        return "关闭标签";
    }
}
