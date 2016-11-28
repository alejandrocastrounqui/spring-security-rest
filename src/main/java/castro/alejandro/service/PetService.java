package castro.alejandro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import castro.alejandro.model.Pet;
import castro.alejandro.repository.PetRepository;
import castro.alejandro.security.service.exception.ActionNotAlowedException;

@Service("petService")
public class PetService {
	
	@Autowired
	PetRepository petRepository;
	
	public List<Pet> getByOwner(String ownername){
		Authentication currentAuthContainer = SecurityContextHolder.getContext().getAuthentication();
        String name = currentAuthContainer.getName();
        if(ownername == null || !name.equals(ownername)){
        	throw new ActionNotAlowedException("");
        }
		return petRepository.findByOwnername(ownername);
	}
}
