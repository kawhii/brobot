package com.karl.brobot.core.cmd.tmvtv;

import com.karl.brobot.core.cmd.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Random;

/**
 * 点击导航
 *
 * @author karl
 * @version 2019-04-25
 */
public class ClickNavCommand implements Command {
    private Random random = new Random();
    /**
     * 可点击单词
     */
    private String words[] = new String[]{
            "传记", "冒险", "剧情", "动作", "犯罪", "喜剧", "伦理", "爱情", "科幻", "奇幻"
    };

    @Override
    public Action[] action() {
        return new Action[]{Action.CLICK};
    }

    @Override
    public void execute(WebDriver webDriver) throws CommandException {
        int idx = random.nextInt(words.length);
        String word = words[idx];
        webDriver.findElement(By.linkText(word)).click();
    }

    @Override
    public String id() {
        return "tmvtv";
    }

    @Override
    public CommandMatcher getMatcher() {
        return (webInfo, platformCode, last) -> "http://www.tmvtv.com/".equals(webInfo.getCurrentUrl()) || ("http" +
                "://www.tmvtv.com").equals(webInfo.getCurrentUrl()) || webInfo.getCurrentUrl().contains("http://www" +
                ".tmvtv.com/movie_bt_tags");
    }

    @Override
    public String name() {
        return "点击导航";
    }
}
