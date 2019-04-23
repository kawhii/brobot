package com.karl.brobot.ip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author karl
 * @version 2019-04-23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DefaultIpCheckerTest {
    @Autowired
    private DefaultIpChecker ipChecker;

    @Test
    public void check() {
        IpInfo ip = new IpInfo();
        ip.setHost("14.215.177.39").setPort(443).setCreateTime(new Date().getTime());
        final boolean check = ipChecker.check(ip);
        assertTrue(check);
    }
}