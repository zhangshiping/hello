package com.company.project;

import com.company.project.authz.domain.shared.BeanProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }
}

