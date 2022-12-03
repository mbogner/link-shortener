package dev.mbo.linkshortener.domain.link

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*

interface LinkRepository : ReactiveCrudRepository<Link, UUID> {

    fun findByCode(code: String): Mono<Link>

    @Modifying
    @Query("UPDATE link SET last_used_at=:at WHERE code=:code AND last_used_at<:at")
    fun updateLastUsedForCode(@Param("code") code: String, @Param("at") at: Instant = Instant.now()): Mono<Long>

    @Query("SELECT * FROM link WHERE last_used_at<:ts")
    fun selectOutdated(ts: Instant): Flux<Link>

    @Query("DELETE FROM link WHERE last_used_at<:ts")
    fun deleteOutdated(ts: Instant): Mono<Long>

}