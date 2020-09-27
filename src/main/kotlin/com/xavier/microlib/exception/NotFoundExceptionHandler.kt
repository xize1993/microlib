package com.xavier.microlib.exception

import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Produces
@Singleton
@Requirements(
        Requires(classes = [NotFoundException::class, ExceptionHandler::class])
)
class NotFoundExceptionHandler : ExceptionHandler<NotFoundException, HttpResponse<*>> {

    override fun handle(request: HttpRequest<*>?, exception: NotFoundException?): HttpResponse<*> {
        return HttpResponse.notFound(exception?.message)
    }

}