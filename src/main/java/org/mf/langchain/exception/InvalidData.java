package org.mf.langchain.exception;

import org.mf.langchain.enums.ProcessStepName;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class InvalidData extends RuntimeException{

    public InvalidData(ProcessStepName step) {
        super("Invalid data for step: " + step);
    }

}
