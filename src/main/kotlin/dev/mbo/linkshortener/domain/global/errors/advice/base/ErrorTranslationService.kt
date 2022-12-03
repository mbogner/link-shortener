package dev.mbo.linkshortener.domain.global.errors.advice.base

import dev.mbo.linkshortener.domain.global.errors.CustomException
import dev.mbo.linkshortener.domain.global.errors.dto.ErrorDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class ErrorTranslationService(
    @Qualifier("messages")
    private val messageSource: MessageSource,
) {

    fun <T : CustomException> translate(exc: T): ErrorDto {
        return ErrorDto(
            title = lookupTitle(exc.code.getCode(), exc.args),
            message = lookupMessage(exc.code.getCode(), exc.args),
            code = exc.code,
            args = exc.args
        )
    }

    private fun lookupTitle(key: String, args: Map<String, Any?>): String {
        return lookupKey("$key.TIT", args)
    }

    private fun lookupMessage(key: String, args: Map<String, Any?>): String {
        return lookupKey("$key.MSG", args)
    }

    private fun lookupKey(key: String, args: Map<String, Any?>): String {
        val lookupKey = key.uppercase()
        return messageSource.getMessage(lookupKey, args.values.toTypedArray(), lookupKey, Locale.getDefault())!!
    }

}