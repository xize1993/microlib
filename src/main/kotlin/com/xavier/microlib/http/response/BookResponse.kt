package com.xavier.microlib.http.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import java.time.LocalDate

/**
 * 書籍一覧と詳細のAPIで使うDTO
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class BookResponse (
        val id: Int,
        val title: String?,
        val isbn: String?,
        val authorId: Int,
        val subject: String?,
        @JsonFormat(pattern = "yyyy-MM-dd") val publicationDate: LocalDate?,
        val haveCover: Boolean?,
        val price: BigDecimal?,
        val description: String?,
        val pageCount: Int?
)
