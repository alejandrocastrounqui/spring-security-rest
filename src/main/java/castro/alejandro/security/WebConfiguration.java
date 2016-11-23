package castro.alejandro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import castro.alejandro.security.session.RestAuthFailureHandler;
import castro.alejandro.security.session.RestAuthSuccessHandler;
import castro.alejandro.security.session.RestAuthenticationProvider;
import castro.alejandro.security.session.RestLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter{
	
	final static String LOGIN_PATH    = "/login";
	final static String LOGOUT_PATH   = "/logout";
	final static String LOGOUT_METHOD = "GET";
	
	@Autowired
	RestAuthSuccessHandler restAuthSuccessHandler;
	
	@Autowired
	RestAuthFailureHandler restAuthFailureHandler;
	
	@Autowired
	RestLogoutSuccessHandler restLogoutSuccessHandler;
	
	@Autowired
	RestAuthenticationProvider restAuthenticationProvider;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(restAuthenticationProvider);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		    .authorizeRequests()
			.antMatchers("/cors").permitAll()
			.antMatchers("/public/**").permitAll()
			.antMatchers("/protected/**").hasAuthority("PARTIAL_AUTHENTICATED")
			.antMatchers("/session/complete").hasAuthority("PARTIAL_AUTHENTICATED")
			.anyRequest().hasAuthority("FULLY_AUTHENTICATED")
		.and()
			.formLogin()
			.permitAll()
			.loginProcessingUrl(LOGIN_PATH)
			.successHandler(restAuthSuccessHandler)
	        .failureHandler(restAuthFailureHandler)
		.and()
	        .logout()
	        .permitAll()
	        .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH, LOGOUT_METHOD))
	        .logoutSuccessHandler(restLogoutSuccessHandler);
		
    }

}
