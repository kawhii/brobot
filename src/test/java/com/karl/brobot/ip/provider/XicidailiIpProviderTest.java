package com.karl.brobot.ip.provider;

import com.karl.brobot.ip.IpInfo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author karl
 * @version 2019-04-23
 */
public class XicidailiIpProviderTest {

    @Test
    public void provide() throws Exception {
        XicidailiIpProvider provider = new XicidailiIpProvider();
        final List<IpInfo> list = provider.provide();
        assertNotNull(list);
    }
}