package dev.ashishwagh.novaspend.exception;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class ApiError {
	private LocalDateTime timeStamp;
	private String error;
	private String message;
	private String path;
	private int status;
	private Map<String,String> errors;
	public ApiError()
	{
		this.timeStamp=LocalDateTime.now();
	}
	public ApiError(String error,String message,String path,int status)
	{
		this.error=error;
		this.message=message;
		this.path=path;
		this.status=status;
	}
	public ApiError(String error,String message,String path,int status,Map<String,String> errors)
	{
		this.error=error;
		this.message=message;
		this.path=path;
		this.status=status;
		this.errors=errors;
	}
}
