package com.xavier.microlib.service

import com.xavier.microlib.domain.Author
import com.xavier.microlib.exception.NotFoundException
import com.xavier.microlib.http.response.AuthorResponse
import com.xavier.microlib.http.request.AuthorRequest
import com.xavier.microlib.repository.AuthorRepository
import com.xavier.microlib.utils.DtoUtils
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 著者のサービスクラス
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Singleton
class AuthorService {

    @Inject
    lateinit var authorRepository: AuthorRepository

    /**
     * 一頁の著者データを返却する
     */
    fun findPage(authorName: String?, pageable: Pageable): Page<AuthorResponse> {
        return authorRepository.findPage(authorName, pageable)
    }

    /**
     * 単特の著者を検索する
     */
    fun findById(id: Int): AuthorResponse {
        authorRepository.findOne(id)?.let {
            return it
        } ?: throw NotFoundException("著者が検索できません。")
    }

    /**
     * 著者を保存する
     */
    fun save(authorRequest: AuthorRequest): AuthorResponse {
        val author = Author(null, authorRequest.authorName, authorRequest.authorNameKana,
                authorRequest.birthday, authorRequest.description,
                true, 1, null, 1, null)
        authorRepository.save(author)
        return DtoUtils.convertAuthorDto(author)
    }

    /**
     * 著者を更新する
     */
    fun update(authorRequest: AuthorRequest): AuthorResponse {
        val author = authorRepository.findById(authorRequest.id!!).get()
        author.authorName = authorRequest.authorName
        author.authorNameKana = authorRequest.authorNameKana
        author.birthday = authorRequest.birthday
        author.description = authorRequest.description
        authorRepository.update(author)
        return DtoUtils.convertAuthorDto(author)
    }

    /**
     * 著者を削除する
     */
    fun delete(id: Int) {
        val author = authorRepository.findById(id).get()
        author.flag = false
        authorRepository.update(author)
    }

}