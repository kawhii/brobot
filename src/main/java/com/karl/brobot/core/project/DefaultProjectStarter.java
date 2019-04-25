package com.karl.brobot.core.project;

import com.karl.brobot.core.cmd.CommandFinder;
import com.karl.brobot.core.cmd.common.OpenCommand;
import com.karl.brobot.filter.*;
import com.karl.brobot.ip.IpInfo;
import com.karl.brobot.ip.ProxyIpManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 默认项目启动信息
 *
 * @author karl
 * @version 2019-04-24
 */
@Component
@Slf4j
public class DefaultProjectStarter implements ProjectStarter {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CommandFinder commandFinder;
    @Autowired
    private ProxyIpManager ipManager;
    private Random random = new Random();
    /**
     * 是否浏览器隐藏执行
     */
    @Value("${robot.headless:true}")
    @Getter
    private boolean headless = true;
    /**
     * 最大执行步数
     */
    private int maxStep = 10;

    @Override
    public void start(IpInfo ipInfo, String name) {
        OpenCommand cmd = new OpenCommand("tmvtv", "http://www.tmvtv.com/", "淘电影");

        DispatchMysticFilter startFilter = new DispatchMysticFilter(cmd);
        startFilter.setCommandFinder(commandFinder);
        Context context = new Context(cmd.id(), ipInfo, System.currentTimeMillis());
        context.setName(name);
        context.setMax(random.nextInt(maxStep) + 1);
        log.debug("机器人{}号,即将执行{}次步数", name, context.getMax());
        WebDriver webDriver = buildWebDriver(ipInfo);

        EndMysticFilter ef = new EndMysticFilter();
        ef.setProxyIpManager(ipManager);
        ef.setApplicationContext(applicationContext);
        List<MysticFilter> filters = new ArrayList<>();
        filters.add(ef);
        filters.add(startFilter);

        FilterChain filterChain = new FilterChain(filters);
        try {
            filterChain.doFiler(context, webDriver);
        } catch (FilterException e) {
            log.warn("机器人{} 发生错误了: {}", name, e.getMessage());
        }
    }

    /**
     * 构建浏览器驱动
     *
     * @param ipInfo
     * @return
     */
    private WebDriver buildWebDriver(IpInfo ipInfo) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(String.format("--proxy-server=%s://%s:%s", ipInfo.getScheme(), ipInfo.getHost(),
                ipInfo.getPort()));
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
        }
        WebDriver webDriver = new ChromeDriver(options);
        //页面加载时间
        webDriver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
        //存留时间
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
        return webDriver;
    }
}
