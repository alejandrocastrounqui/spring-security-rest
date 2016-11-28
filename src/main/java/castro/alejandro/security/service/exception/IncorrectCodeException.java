package castro.alejandro.security.service.exception;

public class IncorrectCodeException extends SecurityException{

	private static final long serialVersionUID = 1L;
	
	public IncorrectCodeException(String message) {
		super(message);
	}
	
}
