package castro.alejandro.security.service;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import castro.alejandro.model.User;
import castro.alejandro.repository.UserRepository;
import castro.alejandro.security.model.RestGrantedAuthority;
import castro.alejandro.security.service.exception.IncorrectCodeException;

@Component("UserService")
public class UserService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);	
	
	@Autowired
	UserRepository userRepository;
	
	private Authentication updateAuthWidth(Authentication oldAuthContainer){
		Collection<? extends GrantedAuthority> oldAuthList = oldAuthContainer.getAuthorities();
		Collection<GrantedAuthority> nextAuthList = new HashSet<GrantedAuthority>();
		for (GrantedAuthority oldAuth : oldAuthList) {
			nextAuthList.add(oldAuth);
		}
		nextAuthList.add(RestGrantedAuthority.FULLY_AUTHENTICATED);
		Authentication nextAuthContainer = new UsernamePasswordAuthenticationToken(oldAuthContainer.getPrincipal(),oldAuthContainer.getCredentials(),nextAuthList);
		return nextAuthContainer;
	}
	
	public void complete(String code){
		Authentication currentAuthContainer = SecurityContextHolder.getContext().getAuthentication();
        String name = currentAuthContainer.getName();
		LOGGER.info("Trying fully authentication for user " + name);
		User user = userRepository.findByUsername(name);
		String userCode = user.getCode();
		if(userCode.equals(code)){
			Authentication nextAuthContainer = updateAuthWidth(currentAuthContainer);
			SecurityContextHolder.getContext().setAuthentication(nextAuthContainer);
		}
		else{
			throw new IncorrectCodeException("");
		}
	}
	
	public User loggedUser(){
		Authentication currentAuthContainer = SecurityContextHolder.getContext().getAuthentication();
        String name = currentAuthContainer.getName();
		LOGGER.info("Retrieving session user " + name);
		User user = userRepository.findByUsername(name);
		return user;
	}

}
