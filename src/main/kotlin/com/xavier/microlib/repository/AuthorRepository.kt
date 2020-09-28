package com.xavier.microlib.repository

import com.xavier.microlib.domain.Author
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
            FROM com.xavier.microlib.domain.Author AS a
            WHERE 
                (:authorName IS NULL OR a.authorName Like CONCAT('%', :authorName, '%'))
                AND a.flag = 1
            ORDER BY a.id DESC
    """, countQuery = """
            SELECT 
                count(*)
            FROM com.xavier.microlib.domain.Author AS a
            WHERE 
                (:authorName IS NULL OR a.authorName Like CONCAT('%', :authorName, '%'))
                AND a.flag = 1
            ORDER BY a.id DESC
    """)
    fun findPage(authorName: String?, pageable: Pageable): Page<Author>

    fun findByIdAndFlagTrue(id: Int): Author?

}