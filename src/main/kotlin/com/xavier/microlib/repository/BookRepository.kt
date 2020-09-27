package com.xavier.microlib.repository

import com.xavier.microlib.domain.Book
import com.xavier.microlib.http.response.BookResponse
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.CrudRepository

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Repository
interface BookRepository : CrudRepository<Book, Int> {

    @Query(value = """
            SELECT 
                b.id AS id, 
                b.title as title, 
                b.isbn as isbn, 
                b.authorId as authorId, 
                b.subject as subject, 
                b.publicationDate as publicationDate,
                b.haveCover as haveCover, 
                b.price as price, 
                b.description as description, 
                b.pageCount as pageCount
            FROM com.xavier.microlib.domain.Book AS b
            WHERE 
                (:title IS NULL OR b.title Like CONCAT('%', :title, '%'))
                AND (:authorId IS NULL OR b.authorId = :authorId)
                AND b.flag = 1
        """, countQuery = """
            SELECT 
                count(*) 
            FROM com.xavier.microlib.domain.Book AS b
            WHERE 
                (:title IS NULL OR b.title Like CONCAT('%', :title, '%'))
                AND (:authorId IS NULL OR b.authorId = :authorId)
                AND b.flag = 1
        """)
    fun findPage(title: String?, authorId: Int?, pageable: Pageable): Page<BookResponse>

    fun findOne(id: Int): BookResponse?
}