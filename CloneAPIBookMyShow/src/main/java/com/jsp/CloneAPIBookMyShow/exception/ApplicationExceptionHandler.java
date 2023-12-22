package com.jsp.CloneAPIBookMyShow.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {  
	
	
		@ExceptionHandler(IdNotFoundException.class)
		public ResponseEntity<ResponseStructure<String>> handleOwnerNotFoundException(IdNotFoundException exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										exception.getMessage(),
										null),
										HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(EmailNotFoundException.class)
		public ResponseEntity<ResponseStructure<String>> handleEmailNotFoundException(EmailNotFoundException exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										exception.getMessage(),
										null),
										HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(MovieNotFoundException.class)
		public ResponseEntity<ResponseStructure<String>> handleMovieNotFoundException(MovieNotFoundException exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										exception.getMessage(),
										null),
										HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(PhoneNumberNotFoundException.class)
		public ResponseEntity<ResponseStructure<String>> handlePhoneNumberNotFoundException(PhoneNumberNotFoundException exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										exception.getMessage(),
										null),
										HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(TheatreNotFoundException.class)
		public ResponseEntity<ResponseStructure<String>> handleTheatreNotFoundException(TheatreNotFoundException exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										exception.getMessage(),
										null),
										HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(OwnerIdNotBeNull.class)
		public ResponseEntity<ResponseStructure<String>> handleOwneridNotBeNull(OwnerIdNotBeNull exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										exception.getMessage(),
										null),
										HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(UnauthorizedOwner.class)
		public ResponseEntity<ResponseStructure<String>> handleUnauthorisedOwner(UnauthorizedOwner exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.UNAUTHORIZED.value(),
										exception.getMessage(),
										null),
										HttpStatus.UNAUTHORIZED);
		}
		
		@ExceptionHandler(DuplicateAddress.class)
		public  ResponseEntity<ResponseStructure<String>> handleDuplicateAddress(DuplicateAddress exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_GATEWAY.value(),
										exception.getMessage(),
										null),
										HttpStatus.BAD_GATEWAY);
		}
		
		@ExceptionHandler(ScreenNotFoundException.class)
		public  ResponseEntity<ResponseStructure<String>> handleScreenNotFoundException(ScreenNotFoundException exception){
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_GATEWAY.value(),
										exception.getMessage(),
										null),
										HttpStatus.BAD_GATEWAY);
		}

		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(
				MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
				
			List<ObjectError> errors = ex.getAllErrors();
			Map<String, String> errorMessages = new HashMap<>();
			for (ObjectError objectError : errors) {
				String fieldName = ((FieldError)objectError).getField();
				String errorMessage  = objectError.getDefaultMessage();
				errorMessages.put(fieldName, errorMessage);
			}
			
			return new ResponseEntity<Object>(new ResponseStructure<Map<String,String>>(HttpStatus.BAD_REQUEST.value(),
																						"Verify the owner info",
																						errorMessages),
																						HttpStatus.BAD_REQUEST);
				
		}
		
		@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
		public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception){
						
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.ALREADY_REPORTED.value(),
										"Duplicate Entry",
										exception.getMessage()),HttpStatus.ALREADY_REPORTED);
		
		}
			
		@ExceptionHandler(ScreenAlreadyAlloted.class)
		public ResponseEntity<ResponseStructure<String>> handleScreenAlreadyAlloted(ScreenAlreadyAlloted exception){
			
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										"Already Screen Alloted..",
										exception.getMessage()),HttpStatus.BAD_REQUEST);

			
		}
				
		@ExceptionHandler(SeatAlreadyBooked.class)
		public ResponseEntity<ResponseStructure<String>> handleSeatAlreadyBooked(SeatAlreadyBooked exception){
			
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										"Seat already booked choose another one...",
										exception.getMessage()),HttpStatus.BAD_REQUEST);
			
		}
	
		@ExceptionHandler(ShowHasCancelledOrClosed.class)
		public ResponseEntity<ResponseStructure<String>> handleShowWasCancelledOrClosed(ShowHasCancelledOrClosed exception){
			
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										"Show already closed or cancelld...",
										exception.getMessage()),HttpStatus.BAD_REQUEST);
			
		}
		
		@ExceptionHandler(ShowIsHousefull.class)
		public ResponseEntity<ResponseStructure<String>> handleShowIsHousefull(ShowIsHousefull exception){
			
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
										"Show Is HouseFull....",
										exception.getMessage()),HttpStatus.BAD_REQUEST);
			
		}
		
		@ExceptionHandler(ShowHasAlreadyStrated.class)
		public ResponseEntity<ResponseStructure<String>> handleShowHasAlreadyStarted(ShowHasAlreadyStrated exception){

			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
																"Show Is already Started....",
																exception.getMessage()),HttpStatus.BAD_REQUEST);

		}
			
		@ExceptionHandler(ShowAlreadyClosed.class)
		public ResponseEntity<ResponseStructure<String>> handleShowAlreadyClosed(ShowAlreadyClosed exception){
			
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
																"Show has already closed...",
																exception.getMessage()),HttpStatus.BAD_REQUEST);

		}
		
		@ExceptionHandler(ShowAlreadyCancelled.class)
		public ResponseEntity<ResponseStructure<String>> handleShowAlreadyCancelled(ShowAlreadyCancelled exception){

			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
																"Show has already cancelled....",
																exception.getMessage()),HttpStatus.BAD_REQUEST);

		}
		
		@ExceptionHandler(TicketAlreadyExpired.class)
		public ResponseEntity<ResponseStructure<String>> handleTicketAlreadyExpired(TicketAlreadyExpired exception){

			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
																"Ticket has already expired....",
																exception.getMessage()),HttpStatus.BAD_REQUEST);

		}
				
		@ExceptionHandler(TicketAlreadyCancelled.class)
		public ResponseEntity<ResponseStructure<String>> handleTicketAlreadyCancelled(TicketAlreadyCancelled exception){

			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
																"Ticket has already cancelled....",
																exception.getMessage()),HttpStatus.BAD_REQUEST);

		}

		@ExceptionHandler(InvalidUsernameAndPasswordException.class)
		public ResponseEntity<ResponseStructure<String>> handleInvalidUsernameAndPasswordException(InvalidUsernameAndPasswordException exception){

			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
																"Fail to login",
																exception.getMessage()),
																HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(SeatsTotalGreaterThanActualSeat.class)
		public ResponseEntity<ResponseStructure<String>> handleSeatsTotalGreaterThanActualSeat(SeatsTotalGreaterThanActualSeat exception){
			
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(),
																"Fail to update screen for update seat info..",
																exception.getMessage()),
																HttpStatus.BAD_REQUEST);
		}
		
}