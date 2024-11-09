package com.example.sookLog.apiPayload.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorReasonDTO {
	private String message;
	private String code;
	private Boolean isSuccess;
	private HttpStatus httpStatus;
}
