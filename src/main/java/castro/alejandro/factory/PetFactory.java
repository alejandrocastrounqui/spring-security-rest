package castro.alejandro.factory;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import castro.alejandro.model.Pet;

@Component("petFactory")
public class PetFactory {

	private static Logger LOGGER = LoggerFactory.getLogger(PetFactory.class);

	private String[] petsString = {
		"Robbert, bruced,   dog",
		"Bruce,   bruced,   dog",
		"Lars,    bruced,   cat",
		"Kirk,    bruced,   hamster",
		"Cliff,   steveh,   cat",
		"Jason,   adrians,  hamster",
		"Lloyd,   davem,    dog"
	};

	public List<Pet> getUsers() {
		List<Pet> pets = new LinkedList<Pet>();
		String petString;
		String[] petStringArray;
		for (int index = 0; index < petsString.length; index++) {
			petString = petsString[index];
			petStringArray = petString.split("(\\s)*\\,(\\s)*");
			Pet pet = createPet(petStringArray);
			LOGGER.info("Create user " + pet.getName());
			pets.add(pet);
		}
		return pets;
	}

	private Pet createPet(String[] petStringArray) {
		String name = petStringArray[0];
		String owner  = petStringArray[1];
		String kind  = petStringArray[2];
		Pet pet = new Pet(name, owner, kind);
		return pet;
	}
}
