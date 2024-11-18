package com.example.sookLog.apiPayload.code.common.exception;
import com.example.sookLog.apiPayload.code.common.BaseErrorCode;
import com.example.sookLog.apiPayload.code.common.ErrorReasonDTO;

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
