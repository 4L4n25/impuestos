package com.impuestos.commons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.impuestos.models.Response;

@ControllerAdvice
public class FileUploadExceptionAdvice {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Response> handleMaxSizeException(MaxUploadSizeExceededException ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Excede el tamaño permitido para xml"));
				
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new Response("Excede el tamaño permitido para xml"));
				
	}
}
