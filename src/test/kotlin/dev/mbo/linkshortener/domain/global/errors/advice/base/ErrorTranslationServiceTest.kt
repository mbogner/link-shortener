package dev.mbo.linkshortener.domain.global.errors.advice.base

import dev.mbo.linkshortener.config.MessagesConfig
import dev.mbo.linkshortener.domain.global.CustomHeaders
import dev.mbo.linkshortener.domain.global.errors.HeaderMissingException
import dev.mbo.linkshortener.domain.global.errors.code.GlobalErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

internal class ErrorTranslationServiceTest {

    private val bean = ErrorTranslationService(MessagesConfig().messageSource())

    @Test
    internal fun translate() {
        val dto = bean.translate(HeaderMissingException(CustomHeaders.X_API_KEY))
        assertThat(dto.title).isEqualTo("Missing Header")
        assertThat(dto.message).isEqualTo("Required header X-Api-Key is missing.")
        assertThat(dto.code).isEqualTo(GlobalErrorCode.HEADER_MISSING)
        assertThat(dto.args).isEqualTo(mapOf("header" to CustomHeaders.X_API_KEY))
    }
}