package com.example.sookLog.apiPayload.exception;
import com.example.sookLog.apiPayload.code.BaseErrorCode;
import com.example.sookLog.apiPayload.code.ErrorReasonDTO;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

	private BaseErrorCode code;

	public GeneralException(String message) {
		super(message);
		this.code = null;
	}

	public GeneralException(BaseErrorCode code) {
		super(code.getReason().getMessage());
		this.code = code;
	}

	public ErrorReasonDTO getErrorReason(){
		return this.code.getReason();
	}

	public ErrorReasonDTO getErrorReasonHttpStatus(){
		return this.code.getReasonHttpStatus();
	}
}
