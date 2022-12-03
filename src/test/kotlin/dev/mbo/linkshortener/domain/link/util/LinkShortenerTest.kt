package dev.mbo.linkshortener.domain.link.util

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

internal class LinkShortenerTest {

    private val bean = LinkShortener()

    @Test
    fun shorten() {
        val results = mutableSetOf<String>()
        (1..100).forEach {
            val code = bean.shorten("https://example.com/$it")
            Assertions.assertThat(code).isNotNull
            Assertions.assertThat(results.add(code)).isTrue()
        }
    }
}