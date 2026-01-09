package dev.ashishwagh.novaspend.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiResponse<T> {
	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timeStamp;
	private ApiResponse(boolean success,String message,T data)
	{
		this.success=success;
		this.message=message;
		this.data=data;
		this.timeStamp=LocalDateTime.now();
	}
	
	public static <T> ApiResponse<T> success(String message,T data)
	{
		return new ApiResponse<T>(true, message, data);
	}
	
	public static <T> ApiResponse<T> success(String message)
	{
		return new ApiResponse<T>(true, message, null);
	}
	
	public static <T> ApiResponse<T> failure(String message)
	{
		return new ApiResponse<T>(false, message, null);
	}
}
