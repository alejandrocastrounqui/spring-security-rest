package castro.alejandro.security.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import castro.alejandro.security.session.filter.RestCorsFilter;
import castro.alejandro.security.session.filter.OkStatusFilterChain;

@Component
public class RestAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	RestCorsFilter restCorsFilter;

    @Override
    public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
	    Authentication authentication
    )throws IOException, ServletException {
    	OkStatusFilterChain okStatusFilterChain = new OkStatusFilterChain(response);
    	restCorsFilter.doFilterInternal(request, response, okStatusFilterChain);
    }
}
