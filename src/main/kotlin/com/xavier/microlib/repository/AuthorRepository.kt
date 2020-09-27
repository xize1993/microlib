package com.xavier.microlib.repository

import com.xavier.microlib.domain.Author
import com.xavier.microlib.http.response.AuthorResponse
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
                AND a.flag = 1
    """, countQuery = """
            SELECT 
                count(*)
            FROM com.xavier.microlib.domain.Author AS a
            WHERE 
                (:authorName IS NULL OR a.authorName Like CONCAT('%', :authorName, '%'))
                AND a.flag = 1
    """)
    fun findPage(authorName: String?, pageable: Pageable): Page<AuthorResponse>

    fun findOne(id: Int): AuthorResponse?

}