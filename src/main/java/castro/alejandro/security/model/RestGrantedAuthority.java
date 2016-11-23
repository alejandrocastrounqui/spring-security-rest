package castro.alejandro.security.model;

import org.springframework.security.core.GrantedAuthority;

public class RestGrantedAuthority implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;

	public static final RestGrantedAuthority PARTIAL_AUTHENTICATED = new RestGrantedAuthority("PARTIAL_AUTHENTICATED");
	public static final RestGrantedAuthority FULLY_AUTHENTICATED = new RestGrantedAuthority("FULLY_AUTHENTICATED");
	
	private String value;

	public RestGrantedAuthority(String value) {
		super();
		this.value = value;
	}

	@Override
	public String getAuthority() {
		return value;
	}

}
