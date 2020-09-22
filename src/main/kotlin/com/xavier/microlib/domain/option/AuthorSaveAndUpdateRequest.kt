package com.xavier.microlib.domain.option

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 著者作成用のリクエストパラメータ
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class AuthorSaveAndUpdateRequest(
        /** ID */
        var id: Int?,

        /** 著者名 */
        var authorName: String,

        /** 著者呼ぶ名（フリカナ） */
        var authorNameKana: String?,

        /** 著者出生日 */
        var birthday: LocalDate?,

        /** 紹介 */
        var description: String?
)