package com.backend.common.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public record ResponseDto<T>(T data) implements Serializable {
    public static <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    public static ResponseEntity<Void> ok() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    public static <T> ResponseEntity<T> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }

    public static ResponseEntity<Void> created() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}