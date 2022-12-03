package dev.mbo.linkshortener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*

@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
class LinkShortenerApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    runApplication<LinkShortenerApplication>(*args)
}
