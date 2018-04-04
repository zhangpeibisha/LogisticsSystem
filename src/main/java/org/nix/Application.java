package org.nix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
/**
 * Create by zhangpe0312@qq.com on 2018/4/4.
 *
 * springBoot启动类
 */
@SpringBootApplication
@ServletComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
