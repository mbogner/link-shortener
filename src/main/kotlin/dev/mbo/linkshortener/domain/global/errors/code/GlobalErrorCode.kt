package dev.mbo.linkshortener.domain.global.errors.code

enum class GlobalErrorCode : ErrorCode {
    HEADER_MISSING,
    HEADER_INVALID,
    ;

    override fun getCode(): String {
        return this.name.uppercase()
    }

}