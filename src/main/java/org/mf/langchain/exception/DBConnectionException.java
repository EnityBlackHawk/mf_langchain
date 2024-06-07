package org.mf.langchain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DBConnectionException  extends RuntimeException{
    public DBConnectionException() {
        super("Unable to connect to the database. Please check your connection settings.");
    }
}
