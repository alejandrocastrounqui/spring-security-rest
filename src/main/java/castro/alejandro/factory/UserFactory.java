package castro.alejandro.factory;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import castro.alejandro.model.User;

@Component("userFactory")
public class UserFactory {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserFactory.class);
	
	private String[] usersString = {
		"Bruce,  Dickinson, bruced,  123456abc, 159",
		"Steve,  Harris,    steveh,  123456abc, 263",
		"Adrian, Smith,     adrians, 123456abc, 278",
		"Dave,   Murray,    davem,   123456abc, 324"
	};

	public List<User> getUsers() {
		List<User> users = new LinkedList<User>();
		String userString;
		String[] usersStringArray;
		for (int index = 0; index < usersString.length; index++) {
			userString = usersString[index];
			usersStringArray = userString.split("(\\s)*\\,(\\s)*");
			User user = createUser(usersStringArray);
			LOGGER.info("Create user " + user.getFirstName());
			users.add(user);
		}
		return users;
	}

	private User createUser(String[] usersStringArray) {
		String firstName = usersStringArray[0];
		String lastName  = usersStringArray[1];
		String nickName  = usersStringArray[2];
		String password  = usersStringArray[3];
		String code      = usersStringArray[4];
		User user = new User(firstName, lastName, nickName, password, code);
		return user;
	}
}
