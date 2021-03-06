package com.karl.brobot.core.cmd.tmvtv;

import com.karl.brobot.core.cmd.*;
import org.openqa.selenium.WebDriver;

/**
 * @author karl
 * @version 2019-04-25
 */
public class BackCommand implements Command {
    @Override
    public Action[] action() {
        return new Action[]{Action.BACK};
    }

    @Override
    public void execute(WebDriver webDriver) throws CommandException {
        webDriver.navigate().back();
    }

    @Override
    public String id() {
        return "tmvtv";
    }

    @Override
    public CommandMatcher getMatcher() {
        //上一个标签有forword就可以返回
        return (webInfo, platformCode, last, wd) -> {
            if (last == null) {
                return false;
            }
            for (Action action : last.action()) {
                if (action == Action.FORWARD) {
                    return true;
                }
            }
            return false;
        };
    }

    @Override
    public String name() {
        return "返回";
    }
}
