package dev.mbo.linkshortener.domain.link.api.dto

import jakarta.validation.constraints.Pattern

// field: required for data class validation
data class NewLinkRequestDto(
    @field:Pattern(regexp = "^https?://.+")
    val url: String,
)