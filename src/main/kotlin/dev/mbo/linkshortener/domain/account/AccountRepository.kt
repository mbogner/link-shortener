package dev.mbo.linkshortener.domain.account

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.*

interface AccountRepository : ReactiveCrudRepository<Account, UUID> {

    fun findByToken(token: String): Mono<Account>

}