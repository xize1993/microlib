package com.xavier.microlib.http.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.micronaut.core.annotation.Introspected
import java.time.LocalDate

/**
 * 著者一覧と詳細のAPIで使うDTO
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class AuthorResponse (
        val id: Int,
        val authorName: String,
        val authorNameKana: String?,
        @JsonFormat(pattern = "yyyy-MM-dd") val birthday: LocalDate?,
        val description: String?
)