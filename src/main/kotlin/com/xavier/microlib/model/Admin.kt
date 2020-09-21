package com.xavier.microlib.model

import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Entity(name = "t_admin")
data class Admin(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        @Column(name = "account", nullable = false)
        var account: String,

        @Column(name = "pwd", nullable = false)
        var pwd: String,

        @Column(name = "last_login_time", nullable = true)
        val lastLoginTime: LocalDateTime
)