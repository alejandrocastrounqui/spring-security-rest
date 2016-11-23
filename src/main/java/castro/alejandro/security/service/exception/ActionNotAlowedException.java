package castro.alejandro.security.service.exception;

public class ActionNotAlowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ActionNotAlowedException() {
        super();
    }

    public ActionNotAlowedException(String message) {
        super(message);
    }
}
