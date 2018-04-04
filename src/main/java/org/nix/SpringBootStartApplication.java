package org.nix;

import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
/**
 * Create by zhangpe0312@qq.com on 2018/4/4.
 *
 * 部署到tomcat
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootStartApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
