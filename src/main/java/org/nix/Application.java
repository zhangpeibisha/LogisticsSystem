package org.nix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Create by zhangpe0312@qq.com on 2018/4/4.
 *
 * springBoot启动类
 */
@SpringBootApplication
@EnableJpaRepositories("org.nix.dao.repositories")
@ComponentScan(basePackages = "org.nix")
@EntityScan("org.nix.entity")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
