package com.github.insanusmokrassar.iobjectk.exceptions

import java.io.IOException

class ReadException : IOException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}