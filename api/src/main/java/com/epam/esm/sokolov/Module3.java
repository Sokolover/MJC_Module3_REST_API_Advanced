package com.epam.esm.sokolov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//        (exclude = {
//                DataSourceAutoConfiguration.class,
//                DataSourceTransactionManagerAutoConfiguration.class,
//                HibernateJpaAutoConfiguration.class})
//@Import(JpaConfig.class)
public class Module3 {
    public static void main(String[] args) {
        SpringApplication.run(Module3.class, args);
    }
}
