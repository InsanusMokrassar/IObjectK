package com.github.insanusmokrassar.iobject.exceptions;

import java.io.IOException;

public class ReadException extends IOException {

    public ReadException(String message) {
        super(message);
    }

    public ReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
