package com.sonpoll.sonpollservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude=[DataSourceAutoConfiguration::class])
class SonpollServiceApplication

fun main(args: Array<String>) {
	runApplication<SonpollServiceApplication>(*args)
}
