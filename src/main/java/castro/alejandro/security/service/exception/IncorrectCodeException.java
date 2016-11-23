package castro.alejandro.security.service.exception;

import org.springframework.security.core.AuthenticationException;

public class IncorrectCodeException extends AuthenticationException{

	private static final long serialVersionUID = 1L;
	
	public IncorrectCodeException(String message) {
		super(message);
	}
	
	public IncorrectCodeException(String message, Throwable throwable) {
		super(message, throwable);
	}


}
