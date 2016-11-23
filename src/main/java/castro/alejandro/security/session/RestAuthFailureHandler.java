package castro.alejandro.security.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import castro.alejandro.security.session.filter.BadRequestStatusFilterChain;
import castro.alejandro.security.session.filter.RestCorsFilter;

@Component("restAuthFailureHandler")
public class RestAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	RestCorsFilter restCorsFilter;

    @Override
    public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
	    AuthenticationException authException
    )throws IOException, ServletException {
    	FilterChain okStatusFilterChain = new BadRequestStatusFilterChain(response);
    	restCorsFilter.doFilterInternal(request, response, okStatusFilterChain);
    }
}