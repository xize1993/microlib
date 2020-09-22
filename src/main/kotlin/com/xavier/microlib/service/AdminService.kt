package com.xavier.microlib.service

import com.xavier.microlib.domain.Admin
import com.xavier.microlib.repository.AdminRepository
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional

/**
 * 管理者のサービスクラス
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Singleton
class AdminService {

    @Inject
    lateinit var adminRepository: AdminRepository

    fun findById(id: Int): Admin = adminRepository.findById(1).orElseThrow()

    @Transactional
    fun save(admin: Admin) = adminRepository.save(admin)
}