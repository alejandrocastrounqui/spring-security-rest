package castro.alejandro.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import castro.alejandro.model.Pet;
import castro.alejandro.repository.PetRepository;
import castro.alejandro.security.service.exception.ActionNotAlowedException;

@RestController
@RequestMapping("/pet")
public class PetController {
	
	@Autowired
	PetRepository petRepository;
	
	@RequestMapping(value="/from/{ownername}", method=RequestMethod.GET)
	public List<Pet> complete(@PathParam("ownername") String ownername){
		Authentication currentAuthContainer = SecurityContextHolder.getContext().getAuthentication();
        String name = currentAuthContainer.getName();
        if(name.equals(ownername)){
        	throw new ActionNotAlowedException();
        }
		return petRepository.findByOwnername(ownername);
	}

}
