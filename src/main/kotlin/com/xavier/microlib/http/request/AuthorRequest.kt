package com.xavier.microlib.http.request

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

/**
 * 著者作成用のリクエストパラメータ
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class AuthorRequest(
        /** ID */
        @field:PositiveOrZero(message = "この著者がありません")
        var id: Int?,

        /** 著者名 */
        @field:NotBlank(message = "著者名を入力してください")
        @field:Size(max=50, message = "著者名は最大{max}桁")
        var authorName: String,

        /** 著者呼ぶ名（フリカナ） */
        @field:Size(max=50, message = "著者呼ぶ名は最大{max}桁")
        var authorNameKana: String?,

        /** 著者出生日 */
        var birthday: LocalDate?,

        /** 紹介 */
        @field:Size(max=1000, message = "紹介は最大{max}桁")
        var description: String?
)