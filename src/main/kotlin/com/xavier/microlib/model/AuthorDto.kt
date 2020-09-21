package com.xavier.microlib.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.annotation.Where
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class AuthorDto (
        val id: Int?,
        val authorName: String,
        val authorNameKana: String?,
        val birthday: LocalDateTime?,
        val description: String?
)