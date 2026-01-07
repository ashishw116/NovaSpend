package dev.ashishwagh.novaspend.exception;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiError {
	private LocalDateTime timeStamp;
	private String error;
	private String message;
	private String path;
	private int status;
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
}
