package com.xavier.microlib

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.xavier.microlib")
		.start()
}

