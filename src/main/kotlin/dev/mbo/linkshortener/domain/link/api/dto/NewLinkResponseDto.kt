package dev.mbo.linkshortener.domain.link.api.dto

data class NewLinkResponseDto(
    val url: String,
    val code: String,
)