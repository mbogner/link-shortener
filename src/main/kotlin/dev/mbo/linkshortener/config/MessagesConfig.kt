package dev.mbo.linkshortener.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.nio.charset.StandardCharsets
import java.util.*

@Configuration
class MessagesConfig {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Bean
    @Qualifier("messages")
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:locale/messages")
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName())
        messageSource.setUseCodeAsDefaultMessage(true)
        return messageSource
    }
}