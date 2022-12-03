package dev.mbo.linkshortener.domain.link.task

import dev.mbo.linkshortener.domain.link.LinkRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class LinkCleanupTask(
    @Value("\${app.tasks.link-cleanup-task.enabled}")
    private val enabled: Boolean,
    @Value("\${app.tasks.link-cleanup-task.config.age-in-seconds}")
    private val ageInSeconds: Long,
    private val linkRepository: LinkRepository,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "\${app.tasks.link-cleanup-task.cron}")
    fun removeOldTasks() {
        if (enabled) {
            val ts = Instant.now().minusSeconds(ageInSeconds)
            log.debug(
                "running task {}: delete links not used after {} ({}s from now)",
                LinkCleanupTask::class.java.simpleName,
                ts,
                ageInSeconds
            )
//            linkRepository.selectOutdated(ts).map {
//                log.debug("delete: {} - {} < {}", it.code, it.lastUsedAt, ts)
//                linkRepository.delete(it)
//            }.subscribe()
            linkRepository.deleteOutdated(ts).map {
                log.debug("deleted {} outdated links that were not used after {}", it, ts)
            }.subscribe()
        }
    }

}