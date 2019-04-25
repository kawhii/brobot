package com.karl.brobot.core.cmd.tmvtv;

import com.karl.brobot.core.cmd.Action;
import com.karl.brobot.core.cmd.Command;
import com.karl.brobot.core.cmd.CommandException;
import com.karl.brobot.core.cmd.CommandMatcher;
import org.openqa.selenium.WebDriver;

import java.util.Random;

/**
 * @author karl
 * @version 2019-04-25
 */
public class RefreshCommand implements Command {
    private Random random = new Random();

    @Override
    public Action[] action() {
        return new Action[]{Action.REFRESH};
    }

    @Override
    public void execute(WebDriver webDriver) throws CommandException {
        webDriver.navigate().refresh();
        //随机休眠5~10秒
        try {
            Thread.sleep((random.nextInt(10) + 1) * 1000);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public String id() {
        return "tmvtv";
    }

    @Override
    public CommandMatcher getMatcher() {
        //上一个标签有forword就可以返回
        return (webInfo, platformCode, last, wd) -> true;
    }

    @Override
    public String name() {
        return "刷新";
    }
}
