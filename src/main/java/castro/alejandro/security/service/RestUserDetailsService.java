package castro.alejandro.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import castro.alejandro.model.User;
import castro.alejandro.repository.UserRepository;
import castro.alejandro.security.model.RestGrantedAuthority;
import castro.alejandro.security.model.RestUserDetails;

@Component("restUserDetailsService")
public class RestUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("");
		}
		RestUserDetails restUserDetails = new RestUserDetails(user);
		restUserDetails.addRole(RestGrantedAuthority.PARTIAL_AUTHENTICATED);
		return restUserDetails;
	}

}
