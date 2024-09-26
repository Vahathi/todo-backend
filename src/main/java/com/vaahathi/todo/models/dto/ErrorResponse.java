package com.vaahathi.todo.models.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String error;
    private List<String> stackTrace;
}


