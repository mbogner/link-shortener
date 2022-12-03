package dev.mbo.linkshortener.domain.account

import dev.mbo.linkshortener.domain.global.entity.AbstractEntity
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.*

@Table("account")
class Account(

    @Length(max = 32)
    @NotBlank
    var name: String,

    @Length(max = 255)
    @NotBlank
    var token: String,

    @Id
    @Column("id")
    var artificialId: UUID? = null,

    @Column("created_at")
    var createdAt: Instant? = Instant.now(),

    @Column("updated_at")
    var updatedAt: Instant? = Instant.now(),

    @Version
    @Column("lock_version")
    var lockVersion: Long? = null
) : AbstractEntity<UUID>() {

    override fun getId(): UUID? {
        return artificialId
    }

    override fun isNew(): Boolean {
        return artificialId == null
    }

}
