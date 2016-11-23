package castro.alejandro.security.model;

import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import castro.alejandro.model.User;


public class RestUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private List<RestGrantedAuthority> roles;
	private String password;
	private String username;
	
	public RestUserDetails(User user) {
		this.password = user.getPassword();
		this.username = user.getUsername();
		this.roles = new LinkedList<RestGrantedAuthority>();
	}
	
	public void addRole(RestGrantedAuthority authority){
		roles.add(authority);
	}

	@Override
	public List<RestGrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
