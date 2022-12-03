package dev.mbo.linkshortener.domain.global.errors.advice.base

import dev.mbo.linkshortener.domain.global.errors.CustomException
import dev.mbo.linkshortener.domain.global.errors.dto.ErrorDto
import org.springframework.http.ResponseEntity

abstract class AbstractExceptionAdvice<T : CustomException>(
    protected val translator: ErrorTranslationService,
) {

    abstract fun handle(exc: T): ResponseEntity<ErrorDto>

}