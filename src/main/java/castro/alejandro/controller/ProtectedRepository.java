package castro.alejandro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedRepository {
	
	@RequestMapping(value="/some/data", method=RequestMethod.GET)
	public String[] someData(){
		String[] someDataResponse = {"protected-one","protected-two"};
		return someDataResponse;
	}

}
