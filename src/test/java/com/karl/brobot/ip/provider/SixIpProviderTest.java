package com.karl.brobot.ip.provider;

import com.karl.brobot.ip.DefaultIpChecker;
import com.karl.brobot.ip.IpInfo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author karl
 * @version 2019-04-23
 */
public class SixIpProviderTest {

    @Test
    public void provide() throws Exception {
        SixIpProvider provider = new SixIpProvider();
        final List<IpInfo> list = provider.provide();
        assertNotNull(list);
        DefaultIpChecker ipChecker = new DefaultIpChecker();
        //list.forEach(ipChecker::check);
    }

}