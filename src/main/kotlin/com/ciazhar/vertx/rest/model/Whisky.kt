package com.ciazhar.vertx.rest.model

import java.util.concurrent.atomic.AtomicInteger



/**
 * Created by ciazhar on 8/28/17.
 */

class Whisky {

    val id: Int

    var name: String? = null

    var origin: String? = null

    constructor(name: String, origin: String) {
        this.id = COUNTER.getAndIncrement()
        this.name = name
        this.origin = origin
    }

    constructor() {
        this.id = COUNTER.getAndIncrement()
    }

    companion object {

        private val COUNTER = AtomicInteger()
    }
}