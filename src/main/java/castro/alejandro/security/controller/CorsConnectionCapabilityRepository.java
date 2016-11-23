package castro.alejandro.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cors")
public class CorsConnectionCapabilityRepository {

	@RequestMapping(method=RequestMethod.GET)
	public String test(){
		return "{\"message\":\"This service assert connection from server outside\"}";
	}
}
