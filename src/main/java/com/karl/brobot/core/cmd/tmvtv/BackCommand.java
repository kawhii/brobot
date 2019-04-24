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
        return (webInfo, platformCode, last) -> webInfo.getCurrentUrl().length() > "http://www.tmvtv.com/".length();
    }

    @Override
    public String name() {
        return "返回";
    }
}
