package com.xavier.microlib.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import com.xavier.microlib.utils.Views
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
@JsonView
data class Author (

        /** ID */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(Views.Public::class)
        val id: Int?,

        /** 著者名 */
        @JsonView(Views.Public::class)
        var authorName: String,

        /** 著者呼ぶ名（フリカナ） */
        @JsonView(Views.Public::class)
        var authorNameKana: String?,

        /** 著者出生日 */
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonView(Views.Public::class)
        var birthday: LocalDate?,

        /** 紹介 */
        @JsonView(Views.Public::class)
        var description: String?,

        /** 論理フラグ */
        var flag: Boolean?,

        /** 作成ユーザID */
        var createUserId: Int?,

        /** 作成日時 */
        @DateCreated
        var createTime: LocalDateTime?,

        /** 更新ユーザID */
        var updateUserId: Int?,

        /** 更新日時 */
        @DateUpdated
        var updateTime: LocalDateTime?,

        /** 著者の書籍リスト */
        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "author", fetch = FetchType.EAGER)
        @JsonIgnoreProperties("author")
        @org.hibernate.annotations.Where(clause = "flag = 1")
        @JsonView(Views.Public::class)
        var books: List<Book>?
)