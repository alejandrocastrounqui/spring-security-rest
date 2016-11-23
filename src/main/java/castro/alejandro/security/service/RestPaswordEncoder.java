package castro.alejandro.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("restPaswordEncoder")
public class RestPaswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		//Paso a minuscula y aplico aes
		return rawPassword+"abc";
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//comparo con algoritmo de matvheo aes
		return (encode(rawPassword)).equals(encodedPassword);
	}


}
