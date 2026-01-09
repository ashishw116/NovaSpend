package dev.ashishwagh.novaspend.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex,HttpServletRequest request)
	{
		ApiError error=new ApiError("Not Found",ex.getMessage(),request.getRequestURI(),404);
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<ApiError> handleUnauthorizeAccesException(UnauthorizedAccessException ex,HttpServletRequest request)
	{
		ApiError error=new ApiError("Forbidden",ex.getMessage(),request.getRequestURI(),403);
		return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ApiError> handleUserAlreadyExistException(UserAlreadyExistException ex,HttpServletRequest request)
	{
		ApiError error=new ApiError("User Already Exist",ex.getMessage(),request.getRequestURI(),409);
		return new ResponseEntity<>(error,HttpStatus.CONFLICT);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,HttpServletRequest request) {
		Map<String,String> fielderrors=new HashMap<>();
		ex.getBindingResult()
		.getFieldErrors()
		.forEach(error-> fielderrors
				.put(error.getField(), error.getDefaultMessage()));
		ApiError error=new ApiError("Validation Failed","Invalid request data",request.getRequestURI(),400,fielderrors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex,HttpServletRequest request) {
		ApiError error=new ApiError("Unexpected Error","Internal Server Error",request.getRequestURI(),500);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
