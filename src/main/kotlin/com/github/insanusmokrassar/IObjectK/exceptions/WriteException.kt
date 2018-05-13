package com.github.insanusmokrassar.IObjectK.exceptions

import java.io.IOException

/**
 * Exception which can be thrown in operations with writing in IOutputObject
 */
class WriteException : IOException {

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}
}
