package com.xavier.microlib.service

import com.xavier.microlib.domain.Author
import com.xavier.microlib.domain.dto.AuthorDto
import com.xavier.microlib.domain.option.AuthorSaveAndUpdateRequest
import com.xavier.microlib.repository.AuthorRepository
import com.xavier.microlib.utils.DtoUtils
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
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
    fun findPage(authorName: String?, pageable: Pageable): Page<AuthorDto> {
        return authorRepository.findPage(authorName, pageable)
    }

    /**
     * 単特の著者を検索する
     */
    fun findById(id: Int): AuthorDto = authorRepository.findOne(id)

    /**
     * 著者を保存する
     */
    fun save(authorSaveAndUpdateRequest: AuthorSaveAndUpdateRequest): AuthorDto {
        val author = Author(null, authorSaveAndUpdateRequest.authorName, authorSaveAndUpdateRequest.authorNameKana,
                authorSaveAndUpdateRequest.birthday, authorSaveAndUpdateRequest.description,
                true, 1, null, 1, null)
        authorRepository.save(author)
        return DtoUtils.convertAuthorDto(author)
    }

    /**
     * 著者を更新する
     */
    fun update(authorSaveAndUpdateRequest: AuthorSaveAndUpdateRequest): AuthorDto {
        val author = authorRepository.findById(authorSaveAndUpdateRequest.id!!).get()
        author.authorName = authorSaveAndUpdateRequest.authorName
        author.authorNameKana = authorSaveAndUpdateRequest.authorNameKana
        author.birthday = authorSaveAndUpdateRequest.birthday
        author.description = authorSaveAndUpdateRequest.description
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