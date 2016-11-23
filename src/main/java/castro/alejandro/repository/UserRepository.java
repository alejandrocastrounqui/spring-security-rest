package castro.alejandro.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import castro.alejandro.factory.UserFactory;
import castro.alejandro.model.User;


@Component("userRepository")
public class UserRepository {
	
	private static Logger LOGGER = Logger.getLogger(UserRepository.class);
	
	@Autowired
	public UserFactory userFactory;
	
	private List<User> users;

	@PostConstruct
	public void initialize(){
		users = userFactory.getUsers();
		LOGGER.info("inicializando");
	}
	
	public User findByUsername(String username){
		for (User user : users) {
			String currentUsername = user.getUsername();
			if(username.equals(currentUsername)){
				return user;
			}
		}
		return null;
	}
}
