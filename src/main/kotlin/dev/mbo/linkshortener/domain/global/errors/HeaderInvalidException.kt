package dev.mbo.linkshortener.domain.global.errors

import dev.mbo.linkshortener.domain.global.errors.code.ErrorCode
import dev.mbo.linkshortener.domain.global.errors.code.GlobalErrorCode
import org.springframework.http.HttpStatus

open class HeaderInvalidException(
    header: String,
    code: ErrorCode = GlobalErrorCode.HEADER_INVALID,
    httpStatus: HttpStatus = HttpStatus.BAD_REQUEST
) : CustomException(
    code = code,
    message = "header $header is invalid",
    args = mapOf("header" to header),
    httpStatus = httpStatus,
)