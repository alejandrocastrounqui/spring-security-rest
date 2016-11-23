package castro.alejandro.security.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import castro.alejandro.security.session.filter.RestCorsFilter;
import castro.alejandro.security.session.filter.UnauthorizedRequestStatusFilterChain;

@Component
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Autowired
	RestCorsFilter restCorsFilter;
	
    @Override
    public void commence(
		HttpServletRequest request, 
		HttpServletResponse response, 
		AuthenticationException authException
	)throws IOException, ServletException {
    	FilterChain okStatusFilterChain = new UnauthorizedRequestStatusFilterChain(response);
    	restCorsFilter.doFilterInternal(request, response, okStatusFilterChain);
    }
}
