package com.xavier.microlib.domain

import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Entity(name = "t_admin")
data class Admin(

        /** ID */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        /** アカウント */
        var account: String,

        /** パスワード */
        var pwd: String,

        /** 最後ログイン時間 */
        val lastLoginTime: LocalDateTime
)