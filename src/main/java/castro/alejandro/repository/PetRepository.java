package castro.alejandro.repository;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import castro.alejandro.factory.PetFactory;
import castro.alejandro.model.Pet;


@Component("petRepository")
public class PetRepository {
	
	private static Logger LOGGER = Logger.getLogger(PetRepository.class);
	
	@Autowired
	public PetFactory petFactory;
	
	private List<Pet> pets;

	@PostConstruct
	public void initialize(){
		pets = petFactory.getUsers();
		LOGGER.info("inicializando");
	}
	
	public List<Pet> findByOwnername(String ownername){
		List<Pet> ownerPets = new LinkedList<Pet>();
		for (Pet pet : pets) {
			String currentOwername = pet.getOwnername();
			if(ownername.equals(currentOwername)){
				ownerPets.add(pet);
			}
		}
		return ownerPets;
	}
}
