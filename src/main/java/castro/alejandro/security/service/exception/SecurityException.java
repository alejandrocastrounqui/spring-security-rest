package castro.alejandro.security.service.exception;

public class SecurityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SecurityException() {
        super();
    }

    public SecurityException(String message) {
        super(message);
    }
}
