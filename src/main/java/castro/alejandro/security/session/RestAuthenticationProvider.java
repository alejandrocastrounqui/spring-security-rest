package castro.alejandro.security.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

import castro.alejandro.security.service.RestPaswordEncoder;
import castro.alejandro.security.service.RestUserDetailsService;

@Component("restAuthenticationProvider")
public class RestAuthenticationProvider extends DaoAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	RestUserDetailsService restUserDetailsService;
	
	@Autowired
	RestPaswordEncoder restPaswordEncoder;
	
	@PostConstruct
	public void initialize() {
		this.setUserDetailsService(restUserDetailsService);
		this.setPasswordEncoder(restPaswordEncoder);
	}


}
