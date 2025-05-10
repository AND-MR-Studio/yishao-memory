package org.space.yavin.alex.yishao.memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({
        "org.space.yavin.alex.yishao.memory.domains.userinfo.repository",
        "org.space.yavin.alex.yishao.memory.domains.conversation.repository",
        "org.space.yavin.alex.yishao.memory.domains.soups.repository"
})
@EntityScan({
        "org.space.yavin.alex.yishao.memory.domains.userinfo.entity",
        "org.space.yavin.alex.yishao.memory.domains.conversation.entity",
        "org.space.yavin.alex.yishao.memory.domains.soups.entity"
})
public class YishaoMemoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(YishaoMemoryApplication.class, args);
    }
}