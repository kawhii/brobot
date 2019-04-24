package com.karl.brobot.filter;

import com.karl.brobot.core.cmd.Command;
import com.karl.brobot.core.cmd.common.OpenCommand;
import com.karl.brobot.ip.IpInfo;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author karl
 * @version 2019-04-24
 */
public class DispatchMysticFilterTest {

    @Test
    public void doFilter() throws FilterException {
        List<Command> cmds = new ArrayList<>();
        cmds.add(new OpenCommand("1", "https://baidu.com", "打开百度"));
        DispatchMysticFilter filter = new DispatchMysticFilter(cmds);
        Context context = new Context("baidu", new IpInfo(), System.currentTimeMillis());
        WebDriver webDriver = new ChromeDriver();


        List<MysticFilter> filters = new ArrayList<>();
        filters.add(new EndMysticFilter());

        FilterChain filterChain = new FilterChain(filters);
        filter.doFilter(context, webDriver, filterChain);
    }

}