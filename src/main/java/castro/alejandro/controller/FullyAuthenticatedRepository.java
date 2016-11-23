package castro.alejandro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticated")
public class FullyAuthenticatedRepository {
	
	@RequestMapping(value="/some/data", method=RequestMethod.GET)
	public String[] someData(){
		String[] someDataResponse = {"private-one","private-two"};
		return someDataResponse;
	}
}
