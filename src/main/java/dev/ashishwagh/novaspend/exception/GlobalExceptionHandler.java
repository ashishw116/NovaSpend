package dev.ashishwagh.novaspend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	@ExceptionHandler(UnauthorizedAccesException.class)
	public ResponseEntity<ApiError> handleUnauthorizeAccesException(UnauthorizedAccesException ex,HttpServletRequest request)
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
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex,HttpServletRequest request)
	{
		ApiError error=new ApiError("Unauthorized","Invalid Email & Password",request.getRequestURI(),401);
		return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex,HttpServletRequest request)
	{
		ApiError error=new ApiError("UnAuthorized","User Not Found",request.getRequestURI(),401);
		return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
