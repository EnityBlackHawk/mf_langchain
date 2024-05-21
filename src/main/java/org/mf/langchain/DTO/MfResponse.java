package org.mf.langchain.DTO;

import java.util.Date;

public record MfResponse(String aiResponse, int tokens, String prompt, Date date) {
}
