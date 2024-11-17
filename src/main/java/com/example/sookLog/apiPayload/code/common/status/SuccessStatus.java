package com.example.sookLog.apiPayload.code.common.status;

import org.springframework.http.HttpStatus;

import com.example.sookLog.apiPayload.code.common.BaseCode;
import com.example.sookLog.apiPayload.code.common.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
	//가장 일반적인 응답
	_OK(HttpStatus.OK, "COMMON200", "성공입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReason(){
		return ReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(true)
			.build()
			;
	}

	@Override
	public ReasonDTO getReasonHttpStatus(){
		return ReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(true)
			.httpStatus(httpStatus)
			.build()
			;
	}

}
