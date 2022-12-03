package dev.mbo.linkshortener.domain.link.api

import dev.mbo.linkshortener.domain.global.CustomHeaders
import dev.mbo.linkshortener.domain.global.errors.HeaderMissingException
import dev.mbo.linkshortener.domain.link.LinkService
import dev.mbo.linkshortener.domain.link.api.dto.NewLinkRequestDto
import dev.mbo.linkshortener.domain.link.api.dto.NewLinkResponseDto
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@RestController
class LinkController(
    private val linkService: LinkService,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun init() {
        log.info("controller initialised")
    }

    @Transactional
    @GetMapping(
        value = ["/l/{code}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun redirectByCode(
        @PathVariable("code") code: String,
    ): Mono<ResponseEntity<Void>> {
        log.info("getting link for code {}", code)
        return linkService.findByCodeAndUpdateLastUsedAt(code)
            .publishOn(Schedulers.boundedElastic())
            .map {
                ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                    .header(HttpHeaders.LOCATION, it.url)
                    .build<Void>()
            }.defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @Transactional
    @PostMapping(
        value = ["/l"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun shortenUrl(
        @RequestBody @Validated url: NewLinkRequestDto,
        @RequestHeader(value = CustomHeaders.X_API_KEY, required = false) apiKey: String?,
    ): Mono<ResponseEntity<NewLinkResponseDto>> {
        log.info("creating code for url {}", url)
        apiKey ?: throw HeaderMissingException(CustomHeaders.X_API_KEY)
        return linkService.create(apiKey, url.url)
            .publishOn(Schedulers.boundedElastic())
            .map {
                ResponseEntity.status(HttpStatus.CREATED).body(
                    NewLinkResponseDto(
                        url = it.url,
                        code = it.code,
                    )
                )
            }
    }

}