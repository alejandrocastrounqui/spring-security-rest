package castro.alejandro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicRepository {
	
	@RequestMapping(value="/some/data", method=RequestMethod.GET)
	public String[] someData(){
		String[] someDataResponse = {"public-one","public-two"};
		return someDataResponse;
	}

}
