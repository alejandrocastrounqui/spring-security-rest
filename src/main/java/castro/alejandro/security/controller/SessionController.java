package castro.alejandro.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import castro.alejandro.security.service.UserService;

@RestController
@RequestMapping("/session")
public class SessionController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/complete", method=RequestMethod.POST)
	public void complete(@RequestParam("code") String code){
		userService.complete(code);
	}
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public @ResponseBody UserDTO user(){
		return userService.loggedUser();
	}

}
