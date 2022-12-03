package dev.mbo.linkshortener.domain.global.errors.code

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GlobalErrorCodeTest {

    @Test
    internal fun getCode() {
        val codes = mutableSetOf<String>()
        GlobalErrorCode.values().forEach {
            val code = it.getCode()
            assertThat(code).isNotNull
            assertThat(code).isEqualTo(code.uppercase())
            assertThat(codes.add(code)).isTrue()
        }
    }
}