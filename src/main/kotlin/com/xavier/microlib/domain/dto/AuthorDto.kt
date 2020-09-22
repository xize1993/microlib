package com.xavier.microlib.domain.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.annotation.Where
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

/**
 * 著者一覧と詳細のAPIで使うDTO
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class AuthorDto (
        val id: Int,
        val authorName: String,
        val authorNameKana: String?,
        val birthday: LocalDate?,
        val description: String?
)