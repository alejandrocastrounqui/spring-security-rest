package castro.alejandro.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("restPaswordEncoder")
public class RestPaswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		//Aplico el algoritmo de encriptación
		return rawPassword+"abc";
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//comparo con algoritmo de comprobación.
		return (encode(rawPassword)).equals(encodedPassword);
	}


}
