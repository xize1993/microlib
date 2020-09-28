package com.xavier.microlib.repository

import com.xavier.microlib.domain.Book
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
            SELECT new com.xavier.microlib.domain.Book(
                b.id, b.title, b.isbn, a, b.subject, b.publicationDate,
                b.haveCover, b.coverImgUrl, b.price, b.description, 
                b.pageCount, b.flag, b.createUserId, b.createTime,
                b.updateUserId, b.updateTime
            )
            FROM com.xavier.microlib.domain.Book AS b
                INNER JOIN com.xavier.microlib.domain.Author AS a
                ON b.author.id = a.id AND a.flag = 1
            WHERE 
                (:title IS NULL OR b.title Like CONCAT('%', :title, '%'))
                AND (:authorId IS NULL OR b.author.id = :authorId)
                AND b.flag = 1
            ORDER BY b.id DESC
        """, countQuery = """
            SELECT 
                count(*) 
            FROM com.xavier.microlib.domain.Book AS b
                INNER JOIN com.xavier.microlib.domain.Author AS a
                ON b.author.id = a.id AND a.flag = 1
            WHERE 
                (:title IS NULL OR b.title Like CONCAT('%', :title, '%'))
                AND (:authorId IS NULL OR b.author.id = :authorId)
                AND b.flag = 1
            ORDER BY b.id DESC
        """)
    fun findPage(title: String?, authorId: Int?, pageable: Pageable): Page<Book>

    fun findByIdAndFlagTrue(id: Int): Book?
}