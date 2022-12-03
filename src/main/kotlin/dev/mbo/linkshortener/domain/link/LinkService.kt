package dev.mbo.linkshortener.domain.link

import dev.mbo.linkshortener.domain.account.AccountRepository
import dev.mbo.linkshortener.domain.global.CustomHeaders
import dev.mbo.linkshortener.domain.global.errors.HeaderInvalidException
import dev.mbo.linkshortener.domain.link.util.LinkShortener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class LinkService(
    private val linkRepository: LinkRepository,
    private val accountRepository: AccountRepository,
    private val linkShortener: LinkShortener,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun findByCodeAndUpdateLastUsedAt(code: String): Mono<Link> {
        log.debug("find link by code: {}", code)
        return Mono.zip(
            linkRepository.findByCode(code)
                .publishOn(Schedulers.boundedElastic()),
            linkRepository.updateLastUsedForCode(code)
                .publishOn(Schedulers.boundedElastic()),
        ).map {
            it.t1
        }
    }

    fun create(apiKey: String, url: String): Mono<Link> {
        return Mono.zip(
            accountRepository.findByToken(apiKey)
                .publishOn(Schedulers.boundedElastic())
                .switchIfEmpty {
                    Mono.error(HeaderInvalidException(CustomHeaders.X_API_KEY))
                },
            linkRepository.save(
                Link(
                    code = linkShortener.shorten(url),
                    url = url,
                )
            ).publishOn(Schedulers.boundedElastic()),
        ).map {
            it.t2
        }
    }

}