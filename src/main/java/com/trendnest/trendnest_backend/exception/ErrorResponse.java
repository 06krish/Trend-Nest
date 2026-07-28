package com.trendnest.trendnest_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String error;
    private String path;
}
// Why do we need ErrorResponse?
//Instead of returning different JSON for every exception, we define one standard format.
//Every error in our application will follow this structure.
