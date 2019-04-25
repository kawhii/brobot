package com.karl.brobot.core.cmd.tmvtv;

import com.karl.brobot.core.cmd.Action;
import com.karl.brobot.core.cmd.Command;
import com.karl.brobot.core.cmd.CommandException;
import com.karl.brobot.core.cmd.CommandMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;


/**
 * 点击电影
 *
 * @author karl
 * @version 2019-04-25
 */
public class MovieClickCommand implements Command {
    private Random random = new Random();

    @Override
    public Action[] action() {
        return new Action[]{Action.NEW_TAB, Action.OPEN};
    }

    @Override
    public void execute(WebDriver webDriver) throws CommandException {
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("mi_btcon")));
        final List<WebElement> miBtcons = webDriver.findElements(By.className("mi_btcon"));
        final WebElement miBtcon = miBtcons.get(random.nextInt(miBtcons.size()));
        final WebElement ul = miBtcon.findElement(By.tagName("ul"));
        if (ul != null) {
            //找出a标签随机点击
            final List<WebElement> links = ul.findElements(By.tagName("a"));
            if (links.size() > 0) {
                links.get(random.nextInt(links.size())).click();
                //随机休眠5~10秒
                try {
                    Thread.sleep((random.nextInt(10) + 5) * 1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    @Override
    public String id() {
        return "tmvtv";
    }

    @Override
    public CommandMatcher getMatcher() {
        //有电影标签即可
        return (webInfo, platformCode, last, wd) -> {
            final List<WebElement> miBtcons = wd.findElements(By.className("mi_btcon"));
            return miBtcons != null && miBtcons.size() > 0;
        };
    }

    @Override
    public String name() {
        return "查看电影";
    }
}
