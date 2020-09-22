package com.xavier.microlib.domain.dto

import com.xavier.microlib.domain.Book
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import java.time.LocalDate

/**
 * 書籍一覧と詳細のAPIで使うDTO
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class BookDto (
        val id: Int,
        val title: String?,
        val isbn: String?,
        val authorId: Int,
        val subject: String?,
        val publicationDate: LocalDate?,
        val coverImgUrl: String?,
        val price: BigDecimal?,
        val description: String?,
        val pageCount: Int?
)
