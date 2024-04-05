package com.xlf;


import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.xlf.mapper")
@EnableCaching
@EnableMPP
@SpringBootApplication
@EnableTransactionManagement
public class Xlf_main {
    public static void main(String[] args) {
        SpringApplication.run(Xlf_main.class, args);

    }
}
