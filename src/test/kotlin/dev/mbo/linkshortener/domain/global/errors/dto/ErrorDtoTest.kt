package dev.mbo.linkshortener.domain.global.errors.dto

import dev.mbo.linkshortener.domain.global.CustomHeaders
import dev.mbo.linkshortener.domain.global.errors.code.GlobalErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class ErrorDtoTest {

    @Test
    internal fun testDto() {
        val dto1 = ErrorDto(
            title = UUID.randomUUID().toString(),
            message = UUID.randomUUID().toString(),
            code = GlobalErrorCode.HEADER_MISSING,
            args = mapOf("header" to CustomHeaders.X_API_KEY),
        )
        val dto2 = ErrorDto(
            title = null,
            message = null,
            code = null,
        )

        assertThat(dto1.toString()).isNotBlank
        assertThat(dto1.hashCode()).isNotNull
        assertThat(dto1).isEqualTo(dto1)
        assertThat(dto1).isNotEqualTo(dto2)
    }

}