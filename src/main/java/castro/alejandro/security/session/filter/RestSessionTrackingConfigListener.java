package castro.alejandro.security.session.filter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

@Component
public class RestSessionTrackingConfigListener implements ServletContextInitializer {

	private static Logger LOGGER = LoggerFactory.getLogger(RestSessionTrackingConfigListener.class);
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		LOGGER.info("configuring session cookie");
		SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
		sessionCookieConfig.setName("MYSESSIONID");
		
	}

}
