package com.github.insanusmokrassar.IObjectK.exceptions

import java.io.IOException

class WriteException : IOException {

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}
}
