package dev.mbo.linkshortener.domain.global.errors.dto

import dev.mbo.linkshortener.domain.global.errors.code.ErrorCode

data class ErrorDto(
    val title: String?,
    val message: String?,
    val code: ErrorCode?,
    val args: Map<String, Any?> = emptyMap(),
)