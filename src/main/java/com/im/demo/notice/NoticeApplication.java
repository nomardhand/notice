package com.im.demo.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.Properties;


@EnableAspectJAutoProxy(proxyTargetClass = true)
//@SpringBootApplication(scanBasePackages = { "com.im.demo" })
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages = {"com.im.demo.notice"})
public class NoticeApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
        // 기존
        // SpringApplication.run(NoticeApplication.class, args);

        // 변경 : 커스텀 Properties 적용
        new SpringApplicationBuilder(NoticeApplication.class)
                .properties(getProperties())
                .build()
                .run(args);
    }

    // SpringBootServletInitializer 클래스
    // SpringBoot에서 War로 배포하려면 SpringBootServletInitializer를 상속
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NoticeApplication.class); //.properties(getProperties());
    }

    // CommandLineRunner 인터페이스
    // 프로젝트를 Run 구동하는 동안에 명령어를 실행하거나 표시해 주는 함수
    @Override
    public void run(String... args) {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String bean : beans) {
            System.out.println(bean);
        }

        String springVersion = org.springframework.core.SpringVersion.getVersion();
        System.out.println("\nSpring Framework Version: " + springVersion);
    }

    // 커스텀 프로퍼티 설정
    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("spring.config.name", "application");
        return props;
    }
}
