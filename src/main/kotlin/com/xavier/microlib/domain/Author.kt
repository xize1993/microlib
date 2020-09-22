package com.xavier.microlib.domain

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
@Entity(name = "t_author")
@Where("flag = true")
data class Author (

        /** ID */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        /** 著者名 */
        var authorName: String,

        /** 著者呼ぶ名（フリカナ） */
        var authorNameKana: String?,

        /** 著者出生日 */
        var birthday: LocalDate?,

        /** 紹介 */
        var description: String?,

        /** 論理フラグ */
        var flag: Boolean,

        /** 作成ユーザID */
        var createUserId: Int,

        /** 作成日時 */
        @DateCreated
        var createTime: LocalDateTime?,

        /** 更新ユーザID */
        var updateUserId: Int,

        /** 更新日時 */
        @DateUpdated
        var updateTime: LocalDateTime?
)