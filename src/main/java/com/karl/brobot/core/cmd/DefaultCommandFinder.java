package com.karl.brobot.core.cmd;

import com.karl.brobot.core.cmd.tmvtv.*;
import com.karl.brobot.filter.Context;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 默认指令寻找器
 *
 * @author karl
 * @version 2019-04-24
 */
public class DefaultCommandFinder implements CommandFinder {
    private Random random = new Random();
    /**
     * 50次最大限制
     */
    private int maxCount = 50;
    /**
     * 指令集合
     */
    private List<Command> commands = new ArrayList<>();

    {
        commands.add(new BackCommand());
        commands.add(new ClickNavCommand());
        commands.add(new ForwardCommand());
        commands.add(new MovieClickCommand());
        commands.add(new RefreshCommand());
        commands.add(new CloseTabCommand());
    }

    @Override
    public Command find(Context context, WebDriver webDriver, Command lastCmd) {
        int findCount = 1;
        do {
            final int idx = random.nextInt(commands.size());
            final Command command = commands.get(idx);
            WebInfo webInfo = new WebInfo();
            webInfo.setCurrentUrl(webDriver.getCurrentUrl());
            webInfo.setTitle(webDriver.getTitle());
            if (command.getMatcher().matcher(webInfo, context.getId(), lastCmd, webDriver)) {
                return command;
            }
        } while (++findCount <= maxCount);
        return null;
    }
}