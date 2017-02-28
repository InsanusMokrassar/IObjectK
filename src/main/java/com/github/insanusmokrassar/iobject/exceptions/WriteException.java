package com.github.insanusmokrassar.iobject.exceptions;

import java.io.IOException;

public class WriteException extends IOException {

    public WriteException(String message) {
        super(message);
    }

    public WriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
