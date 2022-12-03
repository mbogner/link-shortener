package dev.mbo.linkshortener.domain.global.errors.advice

import dev.mbo.linkshortener.domain.global.errors.HeaderInvalidException
import dev.mbo.linkshortener.domain.global.errors.HeaderMissingException
import dev.mbo.linkshortener.domain.global.errors.advice.base.AbstractExceptionAdvice
import dev.mbo.linkshortener.domain.global.errors.advice.base.ErrorTranslationService
import dev.mbo.linkshortener.domain.global.errors.dto.ErrorDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class HeaderInvalidExceptionAdvice(
    translator: ErrorTranslationService,
) : AbstractExceptionAdvice<HeaderInvalidException>(translator) {

    @ExceptionHandler(HeaderInvalidException::class)
    override fun handle(exc: HeaderInvalidException): ResponseEntity<ErrorDto> {
        return ResponseEntity.badRequest().body(translator.translate(exc))
    }

}