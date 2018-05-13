package com.github.insanusmokrassar.IObjectK.exceptions

import java.io.IOException

/**
 * Exception which can be thrown in operations with reading IInputObject
 */
class ReadException : IOException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}