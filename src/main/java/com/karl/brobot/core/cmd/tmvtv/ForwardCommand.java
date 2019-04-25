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
public class ForwardCommand implements Command {
    private Random random = new Random();

    @Override
    public Action[] action() {
        return new Action[]{Action.BACK};
    }

    @Override
    public void execute(WebDriver webDriver) throws CommandException {
        //随机休眠时间
        try {
            //3~13秒
            Thread.sleep((random.nextInt(10) + 3) * 1000);
        } catch (InterruptedException e) {
        }
        webDriver.navigate().forward();

    }

    @Override
    public String id() {
        return "tmvtv";
    }

    @Override
    public CommandMatcher getMatcher() {
        //上一个标签有back就可以返回
        return (webInfo, platformCode, last, wd) -> {
            if (last == null) {
                return false;
            }
            for (Action action : last.action()) {
                if (action == Action.BACK) {
                    return true;
                }
            }
            return false;
        };
    }

    @Override
    public String name() {
        return "前进";
    }
}
