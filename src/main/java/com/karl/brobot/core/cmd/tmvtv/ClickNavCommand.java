package com.karl.brobot.core.cmd.tmvtv;

import com.karl.brobot.core.cmd.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
            "传记", "冒险", "剧情", "动作", "犯罪", "喜剧", "爱情", "科幻", "奇幻", "恐怖", "惊悚"
    };

    @Override
    public Action[] action() {
        return new Action[]{Action.CLICK, Action.FORWARD};
    }

    @Override
    public void execute(WebDriver webDriver) throws CommandException {
        int idx = random.nextInt(words.length);
        String word = words[idx];

        //随机休眠时间
        try {
            Thread.sleep((random.nextInt(10) + 1) * 1000);
        } catch (InterruptedException e) {
        }
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(word)));
        webDriver.findElement(By.linkText(word)).click();
    }

    @Override
    public String id() {
        return "tmvtv";
    }

    @Override
    public CommandMatcher getMatcher() {
        return (webInfo, platformCode, last, wd) -> "http://www.tmvtv.com/".equals(webInfo.getCurrentUrl()) || ("http" +
                "://www.tmvtv.com").equals(webInfo.getCurrentUrl()) || webInfo.getCurrentUrl().contains("http://www" +
                ".tmvtv.com/movie_bt_tags");
    }

    @Override
    public String name() {
        return "点击导航";
    }
}
