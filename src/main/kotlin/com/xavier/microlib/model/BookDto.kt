package com.xavier.microlib.model

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class BookDto (
        val id: Int?,
        val title: String?,
        val isbn: String?,
        val authorId: Int,
        val publicationDate: LocalDate?,
        val description: String?,
        val pageCount: Int?
)