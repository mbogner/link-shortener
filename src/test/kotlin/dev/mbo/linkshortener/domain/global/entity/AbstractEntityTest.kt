package dev.mbo.linkshortener.domain.global.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class AbstractEntityTest {

    private val entity1 = TestEntity(UUID.randomUUID())
    private val entity2 = TestEntity(UUID.randomUUID())
    private val entity3 = TestEntity(entity1.id)

    @Test
    internal fun testEquals() {
        assertThat(entity1).isNotEqualTo(entity2)
        assertThat(entity1).isEqualTo(entity3)
    }

    @Test
    internal fun testHashCode() {
        assertThat(entity1.hashCode()).isNotNull
        assertThat(entity1.hashCode()).isEqualTo(entity1.hashCode())
    }

    @Test
    internal fun testToString() {
        assertThat(entity1.toString()).isNotNull
        assertThat(entity1.toString()).isEqualTo(entity1.toString())

        assertThat(entity1.toString()).isNotEqualTo(entity2.toString())
    }

    internal class TestEntity(
        private var artificialId: UUID?,
    ) : AbstractEntity<UUID>() {
        override fun getId(): UUID? {
            return artificialId
        }

        override fun isNew(): Boolean {
            return null == artificialId
        }

    }
}