package com.a8tiyu.caizhan_kotlin

import javafx.application.Application
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CaizhanKotlinApplication {
    private val log = LoggerFactory.getLogger(Application::class.java)
}

fun main(args: Array<String>) {
    SpringApplication.run(CaizhanKotlinApplication::class.java, *args)
}
