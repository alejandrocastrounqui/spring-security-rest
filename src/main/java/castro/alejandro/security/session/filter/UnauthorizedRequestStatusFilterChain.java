package castro.alejandro.security.session.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class UnauthorizedRequestStatusFilterChain  implements FilterChain{

	private HttpServletResponse originalResponse;
	
	public UnauthorizedRequestStatusFilterChain(HttpServletResponse response) {
		originalResponse = response;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		originalResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
