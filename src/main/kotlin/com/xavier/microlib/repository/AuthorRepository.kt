package com.xavier.microlib.repository

import com.xavier.microlib.domain.Author
import com.xavier.microlib.domain.dto.AuthorDto
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.PageableRepository

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Repository
interface AuthorRepository : PageableRepository<Author, Int> {

    @Query(value = """
            SELECT 
                a.id AS id, 
                a.authorName as authorName, 
                a.authorNameKana as authorNameKana, 
                a.birthday as birthday, 
                a.description as description
            FROM com.xavier.microlib.domain.Author AS a
            WHERE 
                (:authorName IS NULL OR a.authorName Like CONCAT('%', :authorName, '%'))
    """, countQuery = """
            SELECT 
                count(*)
            FROM com.xavier.microlib.domain.Author AS a
            WHERE 
                (:authorName IS NULL OR a.authorName Like CONCAT('%', :authorName, '%'))
    """)
    fun findPage(authorName: String?, pageable: Pageable): Page<AuthorDto>

    fun findOne(id: Int): AuthorDto

}