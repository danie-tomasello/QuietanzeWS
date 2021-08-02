package com.innovat.quietanzeservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.innovat.quietanzeservice.dto.MessageResponse;



@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(BindingException.class)
	public final ResponseEntity<?> exceptionBindingHandler(Exception ex) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		MessageResponse msg = new MessageResponse();
		msg.setCod(HttpStatus.BAD_REQUEST.value());
		msg.setMsg(ex.getMessage());
		return new ResponseEntity<MessageResponse>(msg,headers,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<?> exceptionNotFoundHandler(Exception ex) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		MessageResponse msg = new MessageResponse();
		msg.setCod(HttpStatus.NOT_FOUND.value());
		msg.setMsg(ex.getMessage());
		return new ResponseEntity<MessageResponse>(msg,headers,HttpStatus.NOT_FOUND);
	}
	
		
	@ExceptionHandler(DuplicateException.class)
	public final ResponseEntity<?> exceptionDuplicateHandler(Exception ex) throws JsonProcessingException{
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		MessageResponse msg = new MessageResponse();
		msg.setCod(HttpStatus.NOT_ACCEPTABLE.value());
		msg.setMsg(ex.getMessage());
		return new ResponseEntity<MessageResponse>(msg,headers,HttpStatus.NOT_ACCEPTABLE);
	}
	
	

}
