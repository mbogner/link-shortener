package dev.mbo.linkshortener.domain.global.errors

import dev.mbo.linkshortener.domain.global.errors.code.ErrorCode
import org.springframework.http.HttpStatus

abstract class CustomException(
    val code: ErrorCode,
    message: String? = null,
    val args: Map<String, Any?> = emptyMap(),
    cause: Throwable? = null,
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
) : RuntimeException(
    message,
    cause
)