package dev.mbo.linkshortener.domain.link.util

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class LinkShortener {

    fun shorten(url: String): String {
        return Instant.now().nano.toString(16)
    }

}