package dev.mbo.linkshortener.domain.global.errors

import dev.mbo.linkshortener.domain.global.errors.code.ErrorCode
import dev.mbo.linkshortener.domain.global.errors.code.GlobalErrorCode
import org.springframework.http.HttpStatus

open class HeaderMissingException(
    header: String,
    code: ErrorCode = GlobalErrorCode.HEADER_MISSING,
    httpStatus: HttpStatus = HttpStatus.BAD_REQUEST
) : CustomException(
    code = code,
    message = "header $header missing",
    args = mapOf("header" to header),
    httpStatus = httpStatus,
)