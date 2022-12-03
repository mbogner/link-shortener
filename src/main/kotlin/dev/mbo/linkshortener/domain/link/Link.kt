package dev.mbo.linkshortener.domain.link

import dev.mbo.linkshortener.domain.global.entity.AbstractEntity
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.*

@Table("link")
class Link(

    @Length(max = 16)
    @NotBlank
    @Column("code")
    var code: String,

    @Length(max = 2048)
    @NotBlank
    @Pattern(regexp = "^https?://.+")
    @Column("url")
    var url: String,

    @Id
    @Column("id")
    var artificialId: UUID? = null,

    @Column("created_at")
    var createdAt: Instant? = Instant.now(),

    @Column("last_used_at")
    var lastUsedAt: Instant? = createdAt,
) : AbstractEntity<UUID>() {

    override fun getId(): UUID? {
        return artificialId
    }

    override fun isNew(): Boolean {
        return artificialId == null
    }

}
