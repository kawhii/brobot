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
        if (isHostConnectable(ip.getHost(), ip.getPort(), CONNECT_TIMEOUT_SECONDS)) {
            return true;
        }
        return false;
    }

    /**
     * 检测ip是否可连接
     *
     * @param host
     * @param port
     * @param timeout 超时秒
     * @return
     */
    public static boolean isHostConnectable(String host, int port, int timeout) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port), timeout * 1000);
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
}
