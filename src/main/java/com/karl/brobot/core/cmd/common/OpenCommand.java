package com.karl.brobot.core.cmd.common;

import com.karl.brobot.core.cmd.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 打开指令
 *
 * @author karl
 * @version 2019-04-24
 */
public class OpenCommand implements Command {
    private String id;
    private String url;
    private String name;

    public OpenCommand(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    @Override
    public Action[] action() {
        return new Action[]{Action.OPEN};
    }

    @Override
    public void execute(WebDriver webDriver) throws CommandException {
        webDriver.get(url);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public CommandMatcher getMatcher() {
        return (webInfo, platformCode, last) -> true;
    }

    @Override
    public String name() {
        return name;
    }
}
