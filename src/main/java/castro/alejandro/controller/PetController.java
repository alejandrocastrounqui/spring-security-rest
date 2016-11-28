package castro.alejandro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import castro.alejandro.model.Pet;
import castro.alejandro.service.PetService;

@RestController
@RequestMapping("/pet")
public class PetController {
	
	@Autowired
	PetService petService;
	
	@RequestMapping(value="/from/{ownername}", method=RequestMethod.GET)
	public @ResponseBody List<Pet> getByOwner(@PathVariable String ownername){
		return petService.getByOwner(ownername);
	}

}
