package dev.mbo.linkshortener.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfig(
    private val env: Environment,
) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway(
            Flyway.configure()
                .baselineOnMigrate(env.getRequiredProperty("spring.flyway.baseline-on-migrate", Boolean::class.java))
                .dataSource(
                    env.getRequiredProperty("spring.flyway.url", String::class.java),
                    env.getRequiredProperty("spring.flyway.user", String::class.java),
                    env.getRequiredProperty("spring.flyway.password", String::class.java)
                )
        )
    }

}