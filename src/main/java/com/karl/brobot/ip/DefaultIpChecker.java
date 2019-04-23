package com.karl.brobot.ip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 默认ip检测
 *
 * @author karl
 * @version 2019-04-23
 */
@Slf4j
@Component
public class DefaultIpChecker implements IpChecker {
    @Override
    public boolean check(IpInfo ip) {
        if (isHostReachable(ip.getHost(), CONNECT_TIMEOUT_SECONDS * 1000)
                && isHostConnectable(ip.getHost(),
                ip.getPort())) {
            log.debug("可用IP：[{}:{}]", ip.getHost(), ip.getPort());
            return true;
        }
        return false;
    }

    /**
     * 检测ip是否可连接
     *
     * @param host
     * @param port
     * @return
     */
    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        return true;
    }

    public static boolean isHostReachable(String host, Integer timeOut) {
        try {
            return InetAddress.getByName(host).isReachable(timeOut);
        } catch (IOException e) {
            log.error("", e);
        }
        return false;
    }
}
