package com.karl.brobot.ip.provider;

import com.karl.brobot.ip.IpInfo;
import com.karl.brobot.ip.IpProvider;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author karl
 * @version 2019-04-23
 */
public class Ip89ProviderTest {

    @Test
    public void provide() throws Exception {
        IpProvider ipProvider = new Ip89Provider();
        final List<IpInfo> ips = ipProvider.provide();
        assertNotNull(ips);
    }
}