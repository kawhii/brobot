package com.karl.brobot.config;

import com.karl.brobot.core.cmd.CommandFinder;
import com.karl.brobot.core.cmd.DefaultCommandFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 策略配置
 *
 * @author karl
 * @version 2019-04-25
 */
@Configuration
public class StrategyConfig {
    @Bean
    protected CommandFinder commandFinder() {
        return new DefaultCommandFinder();
    }
}
